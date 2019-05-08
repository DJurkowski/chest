package com.app.chess.chest.security.services.Tournament;

import com.app.chess.chest.message.response.ResponseMessage;
import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.Tournament.TournamentStatus;
import com.app.chess.chest.model.User;
import com.app.chess.chest.model.exceptions.AlreadyExistsException;
import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.exceptions.TourStatusException;
import com.app.chess.chest.model.exceptions.ValueException;
import com.app.chess.chest.repository.TournamentRepository;
import com.app.chess.chest.repository.UserRepository;
import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userService;

    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository, UserRepository userRepository, UserDetailsServiceImpl userService) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public List<Tournament> getTournaments() {
        return (List<Tournament>) tournamentRepository.findAll();
    }

    @Override
    public List<Tournament> getTournamentsWithoutUser(Long userId) {
        List<Tournament> result = new ArrayList<>();
        boolean flag = false;
        for(Tournament tour: tournamentRepository.findAll()) {
            if (!tour.getMasterUser().equals(userId)) {
                for (User userCheck : tour.getUsers()) {
                    if (userCheck.getId().equals(userId)) {
                        flag = true;
                    }
                }
                if (flag == false) {
                    if(TournamentStatus.RECRUTING.equals(tour.getStatus())){
                        result.add(tour);
                    }
                }else {
                    flag = false;
                }
            }
        }
        return result;
    }

    @Override
    public List<Tournament> getUserTournaments(Long userId) {
        User user = userService.getUser(userId);
        return user.getTournaments();
    }

    @Override
    public List<Tournament> getTournamentsByStatus(TournamentStatus status) {
        return tournamentRepository.getTournamentByStatus(status);
    }

    @Override
    public Tournament getTournament(Long id) {
        return tournamentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Tournament.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND));
    }

    @Override
    public void createTournament(Tournament tournament, Long userId) {
        if (!userExistsById(userId)) {
            throw new AlreadyExistsException(Tournament.class.getSimpleName() + AlreadyExistsException.MESSAGE, HttpStatus.BAD_REQUEST);
        } else {
            if(tournament.getMaxNumberOfUser().equals(1)){
                throw new ValueException( "The number of users must be more than 1", HttpStatus.BAD_REQUEST);
            }
            User user = userService.getUser(userId);

            tournament.setMasterUser(userId);
            tournament.setStatus(TournamentStatus.RECRUTING);
            tournament.setNumberOfUser(1);  //master jest pierwszym
            tournament.setMinValueOfRankValue(user.getRankValue());

            user.getTournaments().add(tournament);
            userRepository.save(user);
        }
    }

    @Override
    public Long updateTournament(Tournament tournament, Long userId) {
        if (userExistsById(userId)) {
            if (existsById(tournament.getId())) {
                if(tournament.getMaxNumberOfUser().equals(1) && tournament.getMasterUser().equals(userId)){
                    throw new ValueException( "The number of users must be more than 1", HttpStatus.BAD_REQUEST);
                }
                return tournamentRepository.save(tournament).getId();
            } else {
                throw new NotFoundException(Tournament.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
            }
        } else {
            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Long updateTournament(Tournament tournament) {
        if (existsById(tournament.getId())) {
            return tournamentRepository.save(tournament).getId();
        } else {
            throw new NotFoundException(Tournament.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Tournament addUserToTournament(Long tournamentId, Long userId) {
        User user = userService.getUser(userId);
        Tournament tournament = getTournament(tournamentId);

        if (TournamentStatus.RECRUTING.equals(tournament.getStatus())) {
            if (tournament.getMinValueOfRankValue() <= user.getRankValue()) {
                tournament.setNumberOfUser(tournament.getNumberOfUser() + 1);
                if (tournament.getMaxNumberOfUser().equals(tournament.getNumberOfUser())) {
                    tournament.setStatus(TournamentStatus.FULL);
                }

                user.getTournaments().add(tournament);
                userRepository.save(user);
                return tournamentRepository.save(tournament);
            } else {
                throw new ValueException(User.class.getSimpleName() + ValueException.MESSAGE, HttpStatus.NOT_FOUND);
            }
        } else {
            throw new TourStatusException(Tournament.class.getSimpleName() + TourStatusException.MESSAGE, HttpStatus.NOT_FOUND);//???
        }
    }

    @Override
    public void deleteTournament(Long id) {
        if (existsById(id)) {
            tournamentRepository.deleteById(id);
        } else {
            throw new NotFoundException(Tournament.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    public boolean existsById(Long id) {
        return tournamentRepository.existsById(id);
    }

    public boolean userExistsById(Long userId) {
        return userRepository.existsById(userId);
    }
}
