package com.app.chess.chest.security.services.match;

import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.Tournament.TournamentStatus;
import com.app.chess.chest.model.exceptions.AlreadyExistsException;
import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.match.Match;
import com.app.chess.chest.model.match.MatchStatus;
import com.app.chess.chest.repository.MatchRepository;
import com.app.chess.chest.security.services.Tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TournamentService tournamentService;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, TournamentService tournamentService) {
        this.matchRepository = matchRepository;
        this.tournamentService = tournamentService;
    }

    @Override
    public List<Match> getMatches(Long tournamentId) {
        Tournament tournament = tournamentService.getTournament(tournamentId);
        for(Match m : tournament.getMatches()){
            System.out.println("Mecz:  " + m);
        }
        return tournament.getMatches();
    }

    @Override
    public Match getMatch(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new NotFoundException(Match.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND));
    }

    @Override
    public void createMatch(Match match, Tournament tournament, Long user1Id, Long user2Id) {
        if (match.getId() != null) {
            throw new AlreadyExistsException(Match.class.getSimpleName() + AlreadyExistsException.MESSAGE, HttpStatus.BAD_REQUEST);
        } else {
            match.setTournament(tournament);
            match.setUser1Id(user1Id);
            match.setUser2Id(user2Id);
            match.setStatus(MatchStatus.STANDBY);
            if (!tournament.getStatus().equals(TournamentStatus.STANDBY)) {
                tournament.setStatus(TournamentStatus.STANDBY);
                tournamentService.updateTournament(tournament);
            }
            matchRepository.save(match);
        }
    }

    @Override
    public void deleteMatch(Long id) {
        if(existsById(id)){
            matchRepository.deleteById(id);
        }else {
            throw new NotFoundException(Match.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    public boolean existsById(Long id){ return matchRepository.existsById(id);}
}
