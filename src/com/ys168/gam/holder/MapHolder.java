package com.ys168.gam.holder;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.ys168.gam.model.AreaMap;
import com.ys168.gam.model.IObject;
import com.ys168.gam.model.Item;
import com.ys168.gam.model.Npc;
import com.ys168.gam.model.Room;
import com.ys168.gam.util.MapLoader;

/**
 * 
 * @author Kevin
 * @since 2017年5月19日
 */
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

    public static AreaMap getMap(int roomId) {
        for (AreaMap m : map.values()) {
            if (m.contains(roomId)) {
                return m;
            }
        }
        return null;
    }

    public static Room getRoom(String mapId, int roomId) {
        AreaMap m = getMap(mapId);
        return m == null ? null : m.getRoom(roomId);
    }

    public static void initMap() {
        map.clear();
        map.putAll(MapLoader.load());

        // 尝试初始化NPC
        initRoom(getRoom(9004));
    }

    private static void initRoom(Room room) {
        Set<IObject> objects = new HashSet<>();

        Npc npc1 = new Npc();
        npc1.setId("n" + new Random().nextInt(20000));
        npc1.setName("测试员1");
        npc1.setAttribute("strong", new Random().nextInt(30) + 10);
        npc1.setAttribute("intel", new Random().nextInt(30) + 10);
        npc1.setAttribute("desc", "这是测试员1，目前只用来测试，没有其他作用");

        Npc npc2 = new Npc();
        npc2.setId("n" + new Random().nextInt(20000));
        npc2.setName("闲杂人等");
        npc2.setAttribute("strong", new Random().nextInt(30) + 10);
        npc2.setAttribute("intel", new Random().nextInt(30) + 10);
        npc2.setAttribute("desc", "这是一个不知从哪里跑过来的路人，也不知道来这做什么");

        String id = "i" + new Random().nextInt(10000);
        Item item1 = new Item(id, "长剑");
        item1.setDesc("这是一把长剑");

        objects.add(npc1);
        objects.add(npc2);
        objects.add(item1);

        room.initBaseObjects(objects);
        room.refresh();
    }
}
