package com.app.chess.chest.repository;

import com.app.chess.chest.model.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

}
