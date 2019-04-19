package com.app.chess.chest.controller;

import com.app.chess.chest.security.services.UserDetailsServiceImpl;
import com.app.chess.chest.security.services.match.MatchService;
import com.app.chess.chest.security.services.notification.NotificationService;
import com.app.chess.chest.security.services.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class WebSocketController {

    private final SimpMessagingTemplate template;
    private final RoomService roomService;
    private final UserDetailsServiceImpl userService;
    private final NotificationService notificationService;
    private final MatchService matchService;

    private String[] messageTab = null;
    private String[] messageSwitch = null;
    private String messageOut = "";

    @Autowired
    public WebSocketController(SimpMessagingTemplate template, RoomService roomService, UserDetailsServiceImpl userService, NotificationService notificationService, MatchService matchService) {
        this.template = template;
        this.roomService = roomService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.matchService = matchService;
    }



    @MessageMapping("/websocket/{userToId}")
    public void sendWebSocketMessage(@NotNull String message, @DestinationVariable String userToId) throws IOException{
          messageSwitch = message.split(";",2);
        switch(messageSwitch[0]){
            case "chat":
                messageTab = message.split(";", 5);
                String messageDisplay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ " - " +" @" + messageTab[2] + " : " + messageTab[4];
                messageOut = messageTab[0] + ";" +  messageTab[1] + ";" + messageTab[2] + ";" + messageTab[3] + ";" + messageDisplay;
                this.template.convertAndSend("/privateMessage/" + messageTab[3], messageOut);
                roomService.addMessageToMessageList( messageDisplay, roomService.getRoomId(messageTab[1]));
                System.out.println("message: "+ messageDisplay + " room: "+ messageTab[1]);
                break;
            case "game":
//                dopisac logike do gry
                messageTab = message.split(";", 5);
                messageOut = messageTab[0] + ";" +  messageTab[1] + ";" + userService.getUsername(Long.parseLong(messageTab[2])) + ";" + userService.getUsername(Long.parseLong(messageTab[3])) + ";" + messageTab[4];
                this.template.convertAndSend("/privateMessage/" + userService.getUsername(Long.parseLong(messageTab[3])), messageOut);
                break;
            case "noti":
                messageTab = message.split(";", 5);
                System.out.println("Jestem MEssage Tab" + messageTab[0] + " = noti");
                messageOut = messageTab[0] + ";" +  messageTab[1] + ";" + messageTab[2] + ";" + userService.getUsername(Long.parseLong(messageTab[3])) + ";" + messageTab[4];
                notificationService.createNotification(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " : " + messageTab[4], userService.getUserId(messageTab[2]), Long.parseLong(messageTab[3]));
                this.template.convertAndSend("/privateMessage/" + userService.getUsername(Long.parseLong(messageTab[3])), messageOut);
                break;
            case "ready":
                messageTab = message.split(";", 5);
                System.out.println("Jestem MEssage Tab" + messageTab[0] + " = ready");
                if(Boolean.parseBoolean(messageTab[4])){
                    matchService.userStatusMatchUpdate(matchService.getMatchByName(messageTab[1]), userService.getUserId(messageTab[2]), Boolean.parseBoolean(messageTab[4]));
                } else {
                    matchService.userStatusMatchUpdate(matchService.getMatchByName(messageTab[1]), Long.parseLong(messageTab[3]), Boolean.parseBoolean(messageTab[4]));
                }
                messageOut = messageTab[0] + ";" +  messageTab[1] + ";" + messageTab[2] + ";" + userService.getUsername(Long.parseLong(messageTab[3])) + ";" + messageTab[4];
                this.template.convertAndSend("/privateMessage/" + userService.getUsername(Long.parseLong(messageTab[3])), messageOut);
                break;
            case "startGame":
                messageTab = message.split(";", 5);
                System.out.println("Jestem MEssage Tab" + messageTab[0] + " = startGame");
                messageOut = messageTab[0] + ";" +  messageTab[1] + ";" + messageTab[2] + ";" + userService.getUsername(Long.parseLong(messageTab[3])) + ";" + messageTab[4];
                this.template.convertAndSend("/privateMessage/" + userService.getUsername(Long.parseLong(messageTab[3])), messageOut);
                break;
        }

    }
}
