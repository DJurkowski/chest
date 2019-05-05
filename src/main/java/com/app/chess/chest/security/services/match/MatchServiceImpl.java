package com.app.chess.chest.security.services.match;

import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.Tournament.TournamentStatus;
import com.app.chess.chest.model.exceptions.AlreadyExistsException;
import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.match.Match;
import com.app.chess.chest.model.match.MatchStatus;
import com.app.chess.chest.repository.MatchRepository;
import com.app.chess.chest.security.services.Tournament.TournamentService;
import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TournamentService tournamentService;
    private final UserDetailsServiceImpl userService;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, TournamentService tournamentService, UserDetailsServiceImpl userService) {
        this.matchRepository = matchRepository;
        this.tournamentService = tournamentService;
        this.userService = userService;
    }

    @Override
    public List<Match> getMatches(Long tournamentId) {
        Tournament tournament = tournamentService.getTournament(tournamentId);
        return tournament.getMatches();
    }

    @Override
    public Match getMatch(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new NotFoundException(Match.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND));
    }

    @Override
    public Long getMatchByName(String name) {
        return matchRepository.findByName(name).get().getId();
    }

    @Override
    public void createMatch(Match match, Tournament tournament, Long user1Id, Long user2Id) {
        if (match.getId() != null) {
            throw new AlreadyExistsException(Match.class.getSimpleName() + AlreadyExistsException.MESSAGE, HttpStatus.BAD_REQUEST);
        } else {
            match.setTournament(tournament);
            match.setUserOneId(user1Id);
            match.setuserTwoId(user2Id);
            match.setUserOneReady(false);
            match.setUserTwoReady(false);
            match.setUserOneMoves(0);
            match.setUserTwoMoves(0);
            match.setUserOneRoundsTime(0L);
            match.setUserTwoRoundsTime(0L);
            match.setStatus(MatchStatus.STANDBY);
            if (!tournament.getStatus().equals(TournamentStatus.STANDBY)) {
                tournament.setStatus(TournamentStatus.STANDBY);
                tournamentService.updateTournament(tournament);
            }
            matchRepository.save(match);
            if(match.getName() == null){
                match.setName("match" + match.getId());
                match.setStatus(MatchStatus.STANDBY);
                match.setShowMatch(false);
                match.setWhoWon(0L);
                match.setMatchTime(tournament.getMatchTime());
                matchRepository.save(match);
            }
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

    @Override
    public void updateMatch(Long id, Match match) {
        if(existsById(id)){
            Match matchNow = getMatch(id);
            System.out.println("Jestem w updateMatch przed if");
//            dlatego to kurwa nie wchodzi
            if(!matchNow.getStatus().equals(match.getStatus())){
                matchNow.setStatus(match.getStatus());
            }
//            zmiana
                if(matchNow.getStatus().equals(MatchStatus.STARTED)){
                    matchNow.setStartGameUser(match.getStartGameUser());
                }
                if(matchNow.getStatus().equals(MatchStatus.FINISHED)){
//                    System.out.println("Kto wygral" + matchNow.getWhoWon());
//                    System.out.println("Kto wygral match: " + match.getWhoWon());
                    System.out.println("Jestem w updateMatch po if-ach");
                    if(matchNow.getWhoWon().equals(0L)){

//                        if z ktory wygral
                        if(match.getWhoWon() != 0){
                            matchNow.setWhoWon(match.getWhoWon());
                            userService.userWinMatch(match.getWhoWon());
                        }
                        if(match.getUserOneId().equals(match.getWhoWon())){
                            userService.userLoseMatch(match.getuserTwoId());
                        }else {
                            userService.userLoseMatch(match.getUserOneId());
                        }
                    }
                    if(matchNow.getUserOneMoves().equals(0) && match.getUserOneMoves() != 0){
                        matchNow.setUserOneMoves(match.getUserOneMoves());
                        userService.userMovesCounter(matchNow.getUserOneId(), match.getUserOneMoves());
                    }
                    if(matchNow.getUserTwoMoves().equals(0) && match.getUserTwoMoves() != 0){
                        matchNow.setUserTwoMoves(match.getUserTwoMoves());
                        userService.userMovesCounter(matchNow.getuserTwoId(), match.getUserTwoMoves());
                    }
                    if(matchNow.getUserOneRoundsTime().equals(0L) && match.getUserOneRoundsTime() != 0){
                        matchNow.setUserOneRoundsTime(match.getUserOneRoundsTime());
                        userService.userRoundTimeCounter(matchNow.getUserOneId(), match.getUserOneRoundsTime());
                    }
                    if(matchNow.getUserTwoRoundsTime().equals(0L) && match.getUserTwoRoundsTime() != 0){
                        matchNow.setUserTwoRoundsTime(match.getUserTwoRoundsTime());
                        userService.userRoundTimeCounter(matchNow.getuserTwoId(), match.getUserTwoRoundsTime());

                    }
                    int numberOfMatch = matchNow.getTournament().getMatches().size();
                    int finished = 0;
                    for(Match matchTour: matchNow.getTournament().getMatches()){
                        if(matchTour.getStatus().equals(MatchStatus.FINISHED)){
                            finished++;
                        }
                    }
                    if(finished == numberOfMatch){
                        Tournament tour = matchNow.getTournament();
                        tour.setStatus(TournamentStatus.FINISHED);
                        tournamentService.updateTournament(tour);
                    }
                }

            if(!matchNow.getShowMatch().equals(match.getShowMatch())){
//                System.out.println("Jestme status: " + match.getShowMatch() );
                matchNow.setShowMatch(match.getShowMatch());
            }
            matchRepository.save(matchNow);
        }else {
            throw new NotFoundException(Match.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void userStatusMatchUpdate(Long id, Long userId, Boolean status) {
        if(existsById(id)){
            Match matchNow = getMatch(id);
            if(matchNow.getUserOneId().equals(userId)) {
                matchNow.setUserOneReady(status);
            } else if(matchNow.getuserTwoId().equals(userId)) {
                matchNow.setUserTwoReady(status);
            }
            matchRepository.save(matchNow);
        }else {
        throw new NotFoundException(Match.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }




    public boolean existsById(Long id){ return matchRepository.existsById(id);}
}
