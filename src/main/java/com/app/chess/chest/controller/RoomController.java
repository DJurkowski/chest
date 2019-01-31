package com.app.chess.chest.controller;

import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import com.app.chess.chest.security.services.room.RoomService;
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
    private final RoomService roomService;

    @Autowired
    public RoomController(UserDetailsServiceImpl userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }

    @GetMapping("/user/{userId}/rooms")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getUserRooms(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserRooms(userService.getUserId(userId)));
    }

    @GetMapping("/room/{roomId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getMessages(@PathVariable("roomId") String roomId){
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getMessages(roomService.getRoomId(roomId)));
    }
}
