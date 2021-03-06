package com.app.chess.chest.security.services.room;

import com.app.chess.chest.model.message.Message;
import com.app.chess.chest.model.room.Room;

import java.util.List;
import java.util.Set;

public interface RoomService {
    Room getRoom(Long id);
    void addMessageToMessageList(String message, Long roomId);
    Long getRoomId(String roomName);
    List<Message> getMessages(Long id);
    void delete(Long id);
    Message initMessage(String message, Room room);
}
