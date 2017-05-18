package com.ys168.gam.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ys168.gam.constant.Direction;
import com.ys168.gam.model.AreaMap;
import com.ys168.gam.model.Room;

public class MapLoader {

    private static final Logger log = LoggerFactory.getLogger(MapLoader.class);
    private static final String BASE_DIR = MapLoader.class.getClassLoader().getResource("").toString().substring(6) + "room/";

    public static Map<String, AreaMap> load() {
        Map<String, AreaMap> map = new HashMap<>();

        File baseDir = new File(BASE_DIR);
        if (baseDir.exists() && baseDir.isDirectory()) {
            for (File file : baseDir.listFiles()) {
                String string = file.getAbsolutePath();
                AreaMap chapter = load(string);
                map.put(chapter.getCode(), chapter);
            }
        }
        log.debug("加载到AreaMap个数:" + map.size());
        log.debug("加载到Room个数:" + roomCount(map));
        return map;
    }

    private static AreaMap load(String filePath) {
        String string = loadJSON(filePath);
        return string == null ? null : parse(string);
    }

    private static String loadJSON(String filePath) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    private static AreaMap parse(String string) {
        JSONObject object = JSON.parseObject(string);
        JSONObject globals = object.getJSONObject("globals");
        JSONArray rooms = object.getJSONArray("rooms");

        AreaMap areaMap = new AreaMap();
        areaMap.setCode(globals.getString("mapid"));
        areaMap.setName(globals.getString("mapname"));

        Map<Integer, Room> roomIndexs = parseRoom(rooms);
        int startRoomIndex = globals.getIntValue("startroom");
        Room startRoom = roomIndexs.get(startRoomIndex);

        areaMap.setStartRoomId(startRoom.getId());
        areaMap.addRoom(roomIndexs.values());

        return areaMap;
    }

    private static Map<Integer, Room> parseRoom(JSONArray array) {
        Map<Integer, Room> rooms = new HashMap<>();

        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Integer id = jsonObject.getInteger("id");

            rooms.put(id, parseRoom(jsonObject));
        }

        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Integer id = jsonObject.getInteger("id");

            Room room = rooms.get(id);
            for (Direction direction : Direction.values()) {
                String d = direction.name().toLowerCase();
                Integer nextId = jsonObject.getInteger(d);
                if (nextId == null) {
                    continue;
                }
                Room nextRoom = rooms.get(nextId);
                if (nextRoom != null) {
                    room.initExit(direction, nextRoom);
                }
            }
        }
        return rooms;
    }

    private static Room parseRoom(JSONObject jsonObject) {
        Room room = new Room();
        room.setId(jsonObject.getIntValue("mid"));
        room.setName(jsonObject.getString("nick"));
        room.setDesc(jsonObject.getString("desc"));
        return room;
    }

    private static int roomCount(Map<String, AreaMap> map) {
        int count = 0;
        for (AreaMap m : map.values()) {
            count++;
            count += m.getRooms().size();
        }
        return count;
    }
}
