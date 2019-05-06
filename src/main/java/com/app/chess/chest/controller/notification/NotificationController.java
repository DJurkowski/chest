package com.app.chess.chest.controller.notification;

import com.app.chess.chest.message.response.APIResponse;
import com.app.chess.chest.model.notification.Notification;
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
    private final UserDetailsServiceImpl userService;


    @Autowired
    public NotificationController(NotificationService notificationService, UserDetailsServiceImpl userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}/notifications")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getNotifications(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getNotifications(userId));
    }

    @PutMapping("/user/{userId}/notification/{notiId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity modifyNotification(@PathVariable("userId") String userId, @PathVariable("notiId") Long notiId, @RequestBody Notification notification){
            notificationService.updateNotification(notiId, notification);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    @DeleteMapping("/user/{userId}/notification/{notiId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity deleteNotification(@PathVariable("userId") String userId, @PathVariable("notiId") Long notiId){
        userService.deleteNotification(userService.getUserId(userId), notiId);
        notificationService.deleteNotification(notiId);
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

}
