package com.app.chess.chest.controller.friend;

import com.app.chess.chest.message.response.APIResponse;
import com.app.chess.chest.model.friend.Friend;
import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import com.app.chess.chest.security.services.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FriendController {

    private final UserDetailsServiceImpl userService;

    @Autowired
    public FriendController(UserDetailsServiceImpl userService, FriendService friendService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}/friends")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getUserFriends(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserFriends(userService.getUserId(userId)));
    }

    @GetMapping("/user/{userId}/friendsall")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getListofFriends(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getLifOfFriends(userService.getUserId(userId)));
    }

    @PutMapping("/user/{userId}/friend")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> sendInvitation(@PathVariable("userId") String userId, @Valid @RequestBody Friend friend){
        userService.sendInvitation(userService.getUserId(userId), friend);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }


}
