package com.app.chess.chest.security.services.Tournament;

import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.Tournament.TournamentStatus;

import java.util.List;

public interface TournamentService {
    List<Tournament> getTournaments();
    List<Tournament> getTournamentsWithoutUser(Long userId);
    List<Tournament> getUserTournaments(Long userId);
    List<Tournament> getTournamentsByStatus(TournamentStatus status);
    Tournament getTournament(Long id);
    void createTournament(Tournament tournament, Long userId);
    Long updateTournament(Tournament tournament, Long userId);
    Long updateTournament(Tournament tournament);
    Tournament addUserToTournament(Long tournamentId, Long userId);//id_tournamentu
    void deleteTournament(Long id);
}
