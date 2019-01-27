package com.app.chess.chest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebSocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/{roomId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void sendMessageToPrivateRoom(@NotNull String message, @DestinationVariable String roomId) throws IOException{
        this.template.convertAndSend("/privateRoom/" + roomId, new SimpleDateFormat("HH:mm:ss").format(new Date())+"- "+message);

    }
}
