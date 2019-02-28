package com.app.chess.chest.controller;

import com.app.chess.chest.repository.UserRepository;
import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private final UserDetailsServiceImpl userService;

    @Autowired
    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/username/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getUsername(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsername(userId));
    }

    @GetMapping("/user/{userId}/id")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getUserId(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserId(userId));
    }


}
