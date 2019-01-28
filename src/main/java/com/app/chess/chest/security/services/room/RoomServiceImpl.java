package com.app.chess.chest.security.services.room;

import com.app.chess.chest.model.room.Room;
import com.app.chess.chest.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    public final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void settingRoomsName( List<Room> rooms){

        for(Room room: rooms){
            if(room.getName() == null){
                room.setName("room" + room.getId());
                roomRepository.save(room);
            }
        }

    }
}
