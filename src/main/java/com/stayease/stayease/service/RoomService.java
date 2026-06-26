package com.stayease.stayease.service;

import com.stayease.stayease.model.Room;
import com.stayease.stayease.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByStatus(Room.RoomStatus.AVAILABLE);
    }

    public Room createRoom(Room room) {
        room.setStatus(Room.RoomStatus.AVAILABLE);
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        Room existing = getRoomById(id);
        existing.setNumber(updatedRoom.getNumber());
        existing.setType(updatedRoom.getType());
        existing.setPricePerNight(updatedRoom.getPricePerNight());
        existing.setCapacity(updatedRoom.getCapacity());
        existing.setDescription(updatedRoom.getDescription());
        existing.setStatus(updatedRoom.getStatus());
        return roomRepository.save(existing);
    }

    public void deleteRoom(Long id) {
        getRoomById(id);
        roomRepository.deleteById(id);
    }
}