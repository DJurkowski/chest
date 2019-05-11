package com.app.chess.chest.controller;

import com.app.chess.chest.message.response.APIResponse;
import com.app.chess.chest.message.response.ResponseMessage;
import com.app.chess.chest.model.User;
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
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
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

    @GetMapping("/user/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    @GetMapping("/user/profil/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserProfil(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userService.getUserId(userId)));
    }

    @PutMapping("/user/{userId}/mod")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity userAvailable(@PathVariable("userId") String userId, @RequestBody String available) {
        userService.userAvailable(userId, Boolean.parseBoolean(available));
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));

    }

    @PutMapping("/user/{userId}/edi")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity userEdit(@PathVariable("userId") String userId, @RequestBody String email) {

        if (userRepository.existsByEmail(email)) {
            return new ResponseEntity<>(new ResponseMessage("Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        } else {
            userService.userEdit(userId, email);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userService.getUserId(userId));
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
    }

}
