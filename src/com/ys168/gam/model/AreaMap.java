package com.ys168.gam.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AreaMap {

    private String code;
    private String name;
    private int startRoomId;

    private Map<Integer, Room> rooms = new ConcurrentHashMap<>();

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public boolean contains(int roomId) {
        return rooms.containsKey(roomId);
    }

    public int getStartRoomId() {
        return startRoomId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        if (!rooms.containsKey(room.getId())) {
            this.rooms.put(room.getId(), room);
        }
    }

    public void addRoom(Collection<Room> rooms) {
        for (Room room : rooms) {
            addRoom(room);
        }
    }

    public void setStartRoomId(int startRoomId) {
        this.startRoomId = startRoomId;
    }

    public Room getRoom(int roomId) {
        return rooms.get(roomId);
    }

    public Room getStartRoom() {
        return getRoom(startRoomId);
    }
}
