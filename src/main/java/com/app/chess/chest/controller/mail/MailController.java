package com.app.chess.chest.controller.mail;

import com.app.chess.chest.message.response.APIResponse;
import com.app.chess.chest.message.response.ResponseMessage;
import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import com.app.chess.chest.security.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class MailController {

    private final MailService mailService;
    private final UserDetailsServiceImpl userService;



    @Autowired
    public MailController(MailService mailService, UserDetailsServiceImpl userService) {
        this.mailService = mailService;
        this.userService = userService;
    }

    @PostMapping("/send-mail")
    public ResponseEntity<?> send(@Valid @RequestBody String mail) {

        if(userService.existByMail(mail)){
            try{
                mailService.sendEmail(userService.getUserByMail(mail).get(),userService.resetPassword(mail) );
            }catch(MailException e){
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value()));
        } else {
            return new ResponseEntity<>(new ResponseMessage("Cannot find this address email in our database. Check another one!"),
                    HttpStatus.BAD_REQUEST);
        }

    }

}
