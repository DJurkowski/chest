package com.app.chess.chest.controller.match;

import com.app.chess.chest.message.response.APIResponse;
import com.app.chess.chest.model.match.Match;
import com.app.chess.chest.security.services.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

}
