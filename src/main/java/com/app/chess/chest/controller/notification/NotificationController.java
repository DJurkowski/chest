package com.app.chess.chest.controller.notification;

import com.app.chess.chest.message.response.APIResponse;
import com.app.chess.chest.model.notification.Notification;
import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class NotificationController {

    private final UserDetailsServiceImpl userService;

    @Autowired
    public NotificationController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/user/{userId}/notifications")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getNotifications(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getNotifications(userService.getUserId(userId)));
    }

    @PostMapping("/user/{userId}/notification")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createNotification(@PathVariable("userId") String userId, @Valid @RequestBody Notification notification){
        userService.createNotification(notification);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }
}
