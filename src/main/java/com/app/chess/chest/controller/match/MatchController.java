package com.app.chess.chest.controller.match;

import com.app.chess.chest.message.response.APIResponse;
import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.match.Match;
import com.app.chess.chest.security.services.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PutMapping("/user/{userId}/matches/{matchId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity modifyMatch(@PathVariable("userId") String userId, @PathVariable("matchId") Long matchId, @RequestBody Match match){
        matchService.updateMatch(matchId, match);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    @PutMapping("/user/{userId}/matches/mod/{matchId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity modifyQuickGame(@PathVariable("userId") String userId, @PathVariable("matchId") Long matchId, @RequestBody Match match){
        matchService.updateQuickGame(matchId, match);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    @PostMapping("/user/{userId}/match")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createQuickGame(@PathVariable("userId") String userId, @Valid @RequestBody Match match){

        matchService.createQuickGame(match, userId);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

//    @PostMapping("/user/{userId}/tournaments/{tournamentId}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity addUserToTournament(@PathVariable("userId") String userId, @PathVariable("tournamentId") Long tournamentId, @RequestBody Long tourId){
//        tournamentService.addUserToTournament(tournamentId, userService.getUserId(userId));
//        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
//    }

    @GetMapping("/user/{userId}/usermatches")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getUserMatches(@PathVariable("userId") String userId){return ResponseEntity.status(HttpStatus.OK).body(matchService.getUserQuickGames(userId));}

    @GetMapping("/user/{userId}/matches")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getQuickGames(@PathVariable("userId") String userId){return ResponseEntity.status(HttpStatus.OK).body(matchService.getQuickGames());}


}
