package com.app.chess.chest.repository;

import com.app.chess.chest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String mail);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findAll();

}
