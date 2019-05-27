package com.app.chess.chest.security.services;

import com.app.chess.chest.model.User;
import com.app.chess.chest.model.friend.Friend;
import com.app.chess.chest.repository.UserRepository;
import com.app.chess.chest.security.services.mail.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith( SpringRunner.class )
//@ContextConfiguration
@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl service;

    @MockBean
    private UserRepository userRepository;

    @TestConfiguration
    class UserServiceTestContextConfiguration {

        @Bean
        public UserDetailsServiceImpl userService() {
            return new UserDetailsServiceImpl();
        }

    }

    @Test
    public void getLifOfFriends() {
        User userFriend1 = new User();
        userFriend1.setId(2L);
        User userFriend2 = new User();
        userFriend2.setId(3L);
        User user = new User();
        user.setId(1L);
        Friend friend1 = new Friend(user.getId(), userFriend1.getId(),user.getUsername(), userFriend1.getUsername());
        friend1.setUserOneAccept(true);
        friend1.setUserTwoAccept(true);
        Friend friend2 = new Friend(user.getId(), userFriend2.getId(),user.getUsername(), userFriend2.getUsername());
        friend2.setUserOneAccept(true);
        friend2.setUserTwoAccept(true);
        List<Friend> expectedFriends =  new ArrayList();
        expectedFriends.add(friend1);
        expectedFriends.add(friend2);
        user.setFriends(expectedFriends);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(userFriend1));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(userFriend2));
        List<User> friends = service.getLifOfFriends(1L);

        assertEquals(2,friends.size());
    }
}