package com.app.chess.chest.controller.notification;

import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import com.app.chess.chest.security.services.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;


    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping("/user/{userId}/notifications")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getNotifications(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getNotifications(userId));
    }

//    @PostMapping("/user/{userId}/notification")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<?> createNotification(@PathVariable("userId") String userId, @Valid @RequestBody Notification notification){
//        userService.createNotification(notification);
//        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
//    }
}
