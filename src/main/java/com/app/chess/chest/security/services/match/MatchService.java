package com.app.chess.chest.security.services.match;

import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.match.Match;

import java.util.List;

public interface MatchService {

    List<Match> getMatches(Long tournamentId);
    Match getMatch(Long id);
    Long getMatchByName(String name);
    void createMatch(Match match, Tournament tournament, Long user1Id, Long user2Id);
    void deleteMatch(Long id);
    void updateMatch(Long id, Match match);
    void userStatusMatchUpdate(Long id, Long userId, Boolean status);
}
