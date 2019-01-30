package com.app.chess.chest.security.services.room;

import com.app.chess.chest.model.exceptions.NotFoundException;
import com.app.chess.chest.model.message.Message;
import com.app.chess.chest.model.room.Room;
import com.app.chess.chest.repository.MessageRepository;
import com.app.chess.chest.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {

    public final RoomRepository roomRepository;
    public final MessageRepository messageRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void addMessageToMessageList(String message, Long roomId){
        Room room = getRoom(roomId);
        Message messageResult = new Message();
        messageResult.setMessage(message);
        messageResult.setRoom(room);
        room.getMessages().add(messageResult);
        roomRepository.save(room);
        System.out.println("Room check if message created");
    }

    @Override
    public Long getRoomId(String name) {
        return roomRepository.findByName(name).get().getId();
    }

    @Override
    public Room getRoom(Long id) {
        return roomRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Room.class.getSimpleName() + NotFoundException.MESSAGE, HttpStatus.NOT_FOUND));
    }
}
