package com.app.chess.chest.security.services.match;

import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.match.Match;

import java.util.List;

public interface MatchService {

    List<Match> getMatches(Long tournamentId);
    List<Match> getUserQuickGames(String userId);
    List<Match> getQuickGames();
    Match getMatch(Long id);
    Long getMatchByName(String name);
    void createMatch(Match match, Tournament tournament, Long user1Id, Long user2Id);
    void createQuickGame(Match match, String userOneId);
    void updateQuickGame(Long id, Match match);
    void deleteMatch(Long id);
    void updateMatch(Long id, Match match);
    void userStatusMatchUpdate(Long id, Long userId, Boolean status);
}
