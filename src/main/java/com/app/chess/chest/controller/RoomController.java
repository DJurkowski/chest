package com.app.chess.chest.controller;

import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RoomController {

    private final UserDetailsServiceImpl userService;

    @Autowired
    public RoomController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}/rooms")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getUserRooms(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserRooms(userService.getUserId(userId)));
    }
}
