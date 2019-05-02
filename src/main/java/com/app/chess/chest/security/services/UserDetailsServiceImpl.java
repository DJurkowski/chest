package com.app.chess.chest.security.services;

import com.app.chess.chest.model.User;
import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.notification.Notification;
import com.app.chess.chest.model.room.Room;
import com.app.chess.chest.repository.NotificationRepository;
import com.app.chess.chest.repository.RoomRepository;
import com.app.chess.chest.repository.UserRepository;
import com.app.chess.chest.security.services.room.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoomServiceImpl roomService;
    private final RoomRepository roomRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoomServiceImpl roomService, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomService = roomService;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

        return UserPrinciple.build(user);
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND));
    }

    public String getUsername(Long id) {
        return userRepository
                .findById(id)
                .get()
                .getUsername();
//                .orElseThrow(()-> new NotFoundException(NotFoundException.MESSAGE, HttpStatus.NOT_FOUND));
    }

    public Long getUserId(String username) {
        return userRepository.findByUsername(username).get().getId();
    }

//    public Long modifyUser(User user) {
//        if (existsById(user.getId())) {
//            return userRepository.save(user).getId();
//        } else {
//            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
//        }
//    }

    public void updateUser(String userId, User user){
        if(existsById(getUserId(userId))){
            User userNow = getUser(getUserId(userId));
            if(!userNow.getAvailable().equals(user.getAvailable())){
                userNow.setAvailable(user.getAvailable());
            }
            save(userNow);
        }
    }

    public void userAvailable(String userId, Boolean available) {
        if(existsById(getUserId(userId))){
            User userNow = getUser(getUserId(userId));
            if(!userNow.getAvailable().equals(available)){
                userNow.setAvailable(available);
            }
            save(userNow);
        }
    }

    public void userEdit(String userId, String email) {
        if(existsById(getUserId(userId))){
            User userNow = getUser(getUserId(userId));
            if(!userNow.getEmail().equals(email)){
                userNow.setEmail(email);
            }
            save(userNow);
        }
    }

    public void deleteUser(Long id) {
        if (existsById(id)) {
            User user = getUser(id);
//            Usuwanie pokoji chatu przy usuwaniu uzytkownika
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    public void creatingRooms(User user){
        List<User> users = getUsers();
            for (User userCheck : users) {
                if (!user.equals(userCheck)) {
                    Room room = new Room(user.getUsername(), userCheck.getUsername());
                    roomRepository.save(room);
                    if(room.getName() == null){
                        room.setName("room" + room.getId());
                        roomRepository.save(room);
                    }
                    user.getRooms().add(room);
                    userCheck.getRooms().add(room);
                    userRepository.save(userCheck);
                    userRepository.save(user);
                }
            }
    }

    public List<Room> getUserRooms(Long id){
        User user = getUser(id);

        return user.getRooms();
    }

    public void userWinMatch(Long id){
        if (existsById(id)) {
            User user = getUser(id);
            user.setWins(user.getWins() + 1);
            userRepository.save(user);
        } else {
            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    public void userLoseMatch(Long id){
        if (existsById(id)) {
            User user = getUser(id);
            user.setLosses(user.getLosses() + 1);
            userRepository.save(user);
        } else {
            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }
}
