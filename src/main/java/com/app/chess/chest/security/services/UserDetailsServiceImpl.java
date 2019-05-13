package com.app.chess.chest.security.services;

import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.Tournament.TournamentStatus;
import com.app.chess.chest.model.User;
import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.exceptions.ValueException;
import com.app.chess.chest.model.friend.Friend;
import com.app.chess.chest.model.notification.Notification;
import com.app.chess.chest.model.room.Room;
import com.app.chess.chest.repository.RoomRepository;
import com.app.chess.chest.repository.UserRepository;
import com.app.chess.chest.security.services.friend.FriendService;
import com.app.chess.chest.security.services.room.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String DICTIONARY = "ABCDEFGHIJKLabcdefghijklmnopqrstuvwxyzMNOPQRS0123456789TUVWXYZ";
    private static SecureRandom random = new SecureRandom();

    private final UserRepository userRepository;
    private final RoomServiceImpl roomService;
    private final RoomRepository roomRepository;
    private final FriendService friendService;

    @Autowired
    private PasswordEncoder encoder;


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoomServiceImpl roomService, RoomRepository roomRepository, FriendService friendService) {
        this.userRepository = userRepository;
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.friendService = friendService;
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
        List<User> users = userRepository.findAll();
        Collections.sort(users, Comparator.comparing(User::getWins).reversed());
        return users;
    }



    public User getUser(Long id) {
        System.out.println("Co dostajemy : " + id);
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND));
    }

    public String getUsername(Long id) {
        return userRepository
                .findById(id)
                .get()
                .getUsername();
    }

    public Long getUserId(String username) {
        return userRepository.findByUsername(username).get().getId();
    }

    public Optional<User> getUserByMail(String mail){
            return userRepository.findByEmail(mail);
    }

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

    public void creatingRooms(User user){
        List<User> users = getUsers();
            for (User userCheck : users) {
                if (!user.equals(userCheck)) {
                    Room room = new Room(user.getUsername(), userCheck.getUsername());
                    roomRepository.save(room);
//                    tworzenie przy okazji friend-a
                    addFriend(user.getId(), userCheck.getId());
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

    public List<Friend> getUserFriends(Long id){
        User user = getUser(id);
        return user.getFriends();
    }

    public List<User> getLifOfFriends(Long id){
        User user = getUser(id);
        List<User> userList = new ArrayList<>();
        for(Friend friend: user.getFriends()){
            if(friend.getUserOneAccept().equals(true) && friend.getUserTwoAccept().equals(true)){
                if(friend.getUserOneId().equals(user.getId())){
                    userList.add(getUser(friend.getUserTwoId()));
                }else {
                    userList.add(getUser(friend.getUserOneId()));
                }
            }
        }
        return userList;
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

    public void userMovesCounter(Long id, Integer moves) {
        System.out.println("Moves you: " + moves);
        if(existsById(id)){
            User user = getUser(id);
            user.setMovesSum(user.getMovesSum() + Long.valueOf(moves));
            userRepository.save(user);
        } else {
            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    public void userRoundTimeCounter(Long id, Long time) {
        if(existsById(id)){
            User user = getUser(id);
            user.setRoundTime(user.getRoundTime() + time);
            userRepository.save(user);
        } else {
            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    public void deleteNotification(Long id, Long notificationId){
        if(existsById(id)){
            User user = getUser(id);

            for(Iterator<Notification> it = user.getNotifications().iterator(); it.hasNext();){
                Notification notification = it.next();
                if(notification.getId().equals(notificationId)){
                    it.remove();
                    userRepository.save(user);
                    return;
                }
            }
        } else {
            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    public void deleteUser(Long id) {
        if (existsById(id)) {
            User user = getUser(id);
            for(Tournament tour: user.getTournaments()){
                if(!tour.getStatus().equals(TournamentStatus.FINISHED)){
                    throw new ValueException("You must finish yours tournaments", HttpStatus.BAD_REQUEST);
                }
            }
//            Usuwanie pokoji chatu przy usuwaniu uzytkownika
            for(Iterator<Room> ro = user.getRooms().iterator(); ro.hasNext();){
                Room room = ro.next();
                if(room.getUser1Id().equals(user.getId()) || room.getUser2Id().equals(user.getId())){
                    ro.remove();
                    roomService.delete(room.getId());
                }
            }
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException(User.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    public void addFriend(Long userOneId, Long userTwoId) {
        User userOne = getUser(userOneId);
        User userTwo = getUser(userTwoId);

        Friend friend = new Friend(userOneId, userTwoId, getUsername(userOneId), getUsername(userTwoId));

        friend.setUserOneAccept(false);
        friend.setUserTwoAccept(false);
        friendService.save(friend);

        userOne.getFriends().add(friend);
        userTwo.getFriends().add(friend);
    }


    public void sendInvitation(Long userOneId, Friend friend) {
        Optional<Friend> friendNow = friendService.getFriend(friend.getId());

            if(!friend.getUserOneAccept().equals(friendNow.get().getUserOneAccept())){
                friendNow.get().setUserOneAccept(friend.getUserOneAccept());

            } else if(!friend.getUserTwoAccept().equals(friendNow.get().getUserTwoAccept())){
                friendNow.get().setUserTwoAccept(friend.getUserTwoAccept());
            }
        friendService.save(friendNow.get());

    }

    public String resetPassword(String mail){
        String result = "";
        for (int i = 0; i < 9; i++) {
            int index = random.nextInt(DICTIONARY.length());
            result += DICTIONARY.charAt(index);
        }
        Optional<User> user = getUserByMail(mail);
        user.get().setPassword(encoder.encode(result));
        userRepository.save(user.get());
        System.out.println("Haslo nowe: " + result);
        return result;
    }


    public void save(User user) {
        userRepository.save(user);
    }

    public boolean existsById(Long id){ return userRepository.existsById(id); }

    public Boolean existByMail(String mail) { return userRepository.existsByEmail(mail); }
}
