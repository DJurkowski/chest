package com.app.chess.chest.controller;

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

    @Autowired
    public WebSocketController(SimpMessagingTemplate template, RoomService roomService) {
        this.template = template;
        this.roomService = roomService;
    }

    @MessageMapping("/{roomId}")
    public void sendMessageToPrivateRoom(@NotNull String message, @DestinationVariable String roomId) throws IOException{
        String messageOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" - "+message;
        this.template.convertAndSend("/privateRoom/" + roomId, messageOut);
        roomService.addMessageToMessageList( messageOut, roomService.getRoomId(roomId));
        System.out.println("message: "+ message + " room: "+ roomId);
    }
}
