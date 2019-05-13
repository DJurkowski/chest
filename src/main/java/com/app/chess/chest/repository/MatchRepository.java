package com.app.chess.chest.repository;

import com.app.chess.chest.model.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findByName(String name);
    boolean existsByName(String name);
}
