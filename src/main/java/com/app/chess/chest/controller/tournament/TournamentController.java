package com.app.chess.chest.controller.tournament;


import com.app.chess.chest.message.response.APIResponse;
import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.security.services.Tournament.TournamentService;
import com.app.chess.chest.security.services.UserDetailsServiceImpl;
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
public class TournamentController {

    private final TournamentService tournamentService;
    private final MatchService matchService;
    private final UserDetailsServiceImpl userService;


    @Autowired
    public TournamentController(TournamentService tournamentService, MatchService matchService, UserDetailsServiceImpl userService) {
        this.tournamentService = tournamentService;
        this.matchService = matchService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}/tournament/{tournamentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getTournament(@PathVariable("userId") String userId, @PathVariable("tournamentId") Long tournamentId) {
        return ResponseEntity.status(HttpStatus.OK).body(tournamentService.getTournament(tournamentId));
    }

    @GetMapping("/user/{userId}/usertournaments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getUserTournaments(@PathVariable("userId") String userId){return ResponseEntity.status(HttpStatus.OK).body(tournamentService.getUserTournaments(userService.getUserId(userId)));}

    @GetMapping("/user/{userId}/tournaments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getTournaments(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(tournamentService.getTournamentsWithoutUser(userService.getUserId(userId)));
    }

    @GetMapping("/user/{userId}/alltournaments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getAllTournaments(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(tournamentService.getTournaments());
    }

    @PostMapping("/user/{userId}/tournaments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createTournament(@PathVariable("userId") String userId, @Valid @RequestBody Tournament tournament){

        tournamentService.createTournament(tournament, userService.getUserId(userId));
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    @PutMapping("/user/{userId}/tournaments/{tournamentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity modifyTournament(@PathVariable("userId") String userId, @RequestBody Tournament tournament){
        tournamentService.updateTournament(tournament, userService.getUserId(userId));
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    @DeleteMapping("/user/{userId}/tournaments/{tournamentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity deleteTournament(@PathVariable("userId") String userId, @PathVariable("tournamentId") Long tournamentId){
        tournamentService.deleteTournament(tournamentId);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    @PostMapping("/user/{userId}/tournaments/{tournamentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity addUserToTournament(@PathVariable("userId") String userId, @PathVariable("tournamentId") Long tournamentId, @RequestBody Long tourId){
        tournamentService.addUserToTournament(tournamentId, userService.getUserId(userId));
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    @GetMapping("/user/{userId}/tournaments/{tournamentId}/matches")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getMatches(@PathVariable("userId") String userId, @PathVariable("tournamentId") Long tournamentId){
        return ResponseEntity.status(HttpStatus.OK).body(matchService.getMatches(tournamentId));
    }
}