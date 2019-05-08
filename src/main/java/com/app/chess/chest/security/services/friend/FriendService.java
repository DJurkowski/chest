package com.app.chess.chest.security.services.friend;

import com.app.chess.chest.model.friend.Friend;

import java.util.Optional;


public interface FriendService {

    Optional<Friend> getFriend(Long id);
    void delete(Long id);
    void save(Friend friend);
}
