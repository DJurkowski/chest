package com.app.chess.chest.controller;

import com.app.chess.chest.security.services.UserDetailsServiceImpl;
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

    @Autowired
    public WebSocketController(SimpMessagingTemplate template, RoomService roomService, UserDetailsServiceImpl userService) {
        this.template = template;
        this.roomService = roomService;
        this.userService = userService;
    }

    @MessageMapping("/{roomId}")
    public void sendMessageToPrivateRoom(@NotNull String message, @DestinationVariable String roomId) throws IOException{
        String messageOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" - "+message;
        this.template.convertAndSend("/privateRoom/" + roomId, messageOut);
        roomService.addMessageToMessageList( messageOut, roomService.getRoomId(roomId));
        System.out.println("message: "+ message + " room: "+ roomId);
    }

    @MessageMapping ("/game/{gameRoomId}")
    public void sendMessageToGameRoom(@NotNull String message, @DestinationVariable String gameRoomId) throws IOException{
        this.template.convertAndSend("/gameRoom/"+gameRoomId, message);
        System.out.println(" message:" + message + " gameRoom: "+ gameRoomId);
    }

    @MessageMapping("/notifi/{userToId}")
    public void sendNotificationMessage(@NotNull String message, @DestinationVariable Long userToId) throws IOException{
        this.template.convertAndSend("/notification/" + userService.getUsername(userToId), message);

    }
}
