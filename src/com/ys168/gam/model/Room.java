package com.ys168.gam.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.annotation.JSONField;
import com.ys168.gam.constant.Direction;
import com.ys168.gam.constant.RoomStatus;
import com.ys168.gam.util.MudSystemException;

public class Room implements Cloneable {

    @JSONField(serialize = false)
    private Map<String, IObject> baseObjects = new HashMap<>();// 原始的对象

    private int id;

    private String name;
    private String desc;
    private Map<Direction, RoomInfo> exits;

    private Map<String, IObject> objects;
    @JSONField(serialize = false)
    private RoomStatus status;

    @JSONField(serialize = false)
    private boolean isNeedRefresh;

    public Room() {
        exits = new ConcurrentHashMap<>();
        objects = new ConcurrentHashMap<>();
        setStatus(RoomStatus.OPENED);
    }

    public Room(int id, String name, String desc) {
        this();
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public void addObject(IObject object) {
        if (!hasObject(object)) {
            this.objects.put(object.getId(), object);
            this.isNeedRefresh = true;
        }
    }

    protected Room clone() {
        try {
            Room room = (Room) super.clone();
            room.isNeedRefresh = false;
            return room;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public Room excludeClone(IObject exclude) {
        Room clone = clone();
        if (clone != null) {
            clone.objects = new ConcurrentHashMap<>(this.objects);
            if (clone.hasObject(exclude)) {
                clone.objects.remove(exclude.getId());
            }
        }
        return clone;
    }

    public String getDesc() {
        return desc;
    }

    public Map<String, String> getExits() {
        Map<String, String> map = new HashMap<>();
        for (Direction key : exits.keySet()) {
            map.put(key.name().toLowerCase(), exits.get(key).getName());
        }
        return map;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JSONField(serialize = false)
    public RoomInfo getNextRoom(Direction direction) {
        return exits.get(direction);
    }

    public List<IObject> getObjects() {
        List<IObject> objects = new ArrayList<>(this.objects.values());
        Collections.sort(objects, new Comparator<IObject>() {
            @Override
            public int compare(IObject o1, IObject o2) {
                return (o1.getType() + o1.getId()).compareTo((o2.getType() + o2.getId()));
            }
        });
        return objects;
    }

    public RoomStatus getStatus() {
        return status;
    }

    @JSONField(serialize = false)
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (IObject object : this.objects.values()) {
            if (User.class.getSimpleName().toLowerCase().equals(object.getType())) {
                users.add((User) object);
            }
        }
        return users;
    }

    public boolean hasObject(IObject object) {
        return this.objects.containsKey(object.getId());
    }

    public void initBaseObjects(Set<IObject> baseObjects) {
        if (this.baseObjects.size() > 0) {
            throw new MudSystemException("baseObjects初始化异常");
        }
        for (IObject object : baseObjects) {
            if (User.class.getSimpleName().toLowerCase().equals(object.getType())) {
                throw new MudSystemException("baseObjects初始化异常");
            }
            this.baseObjects.put(object.getId(), object);
        }
    }

    public void initExit(Direction direction, Room room) {
        exits.put(direction, room.toSimpleInfo());
        Direction reverse = direction.reverse();
        if (!room.exits.containsKey(reverse)) {
            room.initExit(reverse, this);
        }
    }

    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }

    /**
     * 用于定期更新
     */
    public void refresh() {
        for (String key : baseObjects.keySet()) {
            if (!objects.containsKey(key)) {
                IObject object = baseObjects.get(key);
                objects.put(object.getId(), object.clone());
            }
        }
    }

    public void refreshFinished() {
        this.isNeedRefresh = false;
    }

    public void removeObject(IObject object) {
        this.objects.remove(object.getId());
        this.isNeedRefresh = true;
    }

    public void setDesc(String description) {
        this.desc = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public RoomInfo toSimpleInfo() {
        return new RoomInfo(getId(), getName());
    }
}
