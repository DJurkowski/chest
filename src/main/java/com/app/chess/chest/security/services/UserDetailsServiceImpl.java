package com.app.chess.chest.security.services;

import com.app.chess.chest.model.User;
import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.room.Room;
import com.app.chess.chest.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

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

    public Long modifyUser(User user) {
        if (existsById(user.getId())) {
            return userRepository.save(user).getId();
        } else {
            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
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
                    user.getRooms().add(room);
                    userCheck.getRooms().add(room);
//                    userRepository.save(userCheck);
                }
            }
            userRepository.save(user);
    }

    public List<Room> getUserRooms(Long id){
        User user = getUser(id);
        return user.getRooms();
    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }
}
