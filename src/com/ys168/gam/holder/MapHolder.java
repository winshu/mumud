package com.ys168.gam.holder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.ys168.gam.model.AreaMap;
import com.ys168.gam.model.IObject;
import com.ys168.gam.model.Npc;
import com.ys168.gam.model.Room;
import com.ys168.gam.util.MapLoader;

public class MapHolder {

    private static Map<String, AreaMap> map = new ConcurrentHashMap<>();

    public static AreaMap getMap(String mapId) {
        return map.get(mapId);
    }

    public static Room getRoom(int roomId) {
        for (AreaMap m : map.values()) {
            if (m.contains(roomId)) {
                return m.getRoom(roomId);
            }
        }
        return null;
    }

    public static Room getRoom(String mapId, int roomId) {
        AreaMap m = getMap(mapId);
        return m == null ? null : m.getRoom(roomId);
    }

    public static Room getStartRoom(String mapId) {
        AreaMap m = getMap(mapId);
        return m == null ? null : m.getStartRoom();
    }

    public static void initMap() {
        map.putAll(MapLoader.load());

        // 尝试初始化NPC
        initRoom(getRoom(29));
    }

    private static void initRoom(Room room) {
        Set<IObject> objects = new HashSet<>();

        Npc npc1 = new Npc();
        npc1.setId("n" + new Random().nextInt(20000));
        npc1.setName("测试员1");

        Npc npc2 = new Npc();
        npc2.setId("n" + new Random().nextInt(20000));
        npc2.setName("测试员2");

        objects.add(npc1);
        objects.add(npc2);

        room.initBaseObjects(objects);
        room.refresh();
    }

    public static List<Room> getAllRoom() {
        List<Room> rooms = new ArrayList<>();
        for (AreaMap m : map.values()) {
            for (Room room : m.getRooms().values()) {
                rooms.add(room);
            }
        }
        return rooms;
    }
}
