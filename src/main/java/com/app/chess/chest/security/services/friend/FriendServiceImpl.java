package com.app.chess.chest.security.services.friend;

import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.friend.Friend;
import com.app.chess.chest.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;


    @Autowired
    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;

    }

    @Override
    public Optional<Friend> getFriend(Long id) {
        if(existsById(id)) return friendRepository.findById(id);
        else {
            throw new NotFoundException(Friend.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void delete(Long id) {
        if(existsById(id)){
            friendRepository.deleteById(id);
        }else {
            throw new NotFoundException(Friend.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void save(Friend friend) {
        friendRepository.save(friend);
    }

    public boolean existsById(Long id){ return friendRepository.existsById(id);}
}
