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
import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.constant.Direction;
import com.ys168.gam.exception.MudVerifyException;
import com.ys168.gam.simple.SimpleObjectInfo;
import com.ys168.gam.simple.SimpleRoomInfo;

/**
 * 房间
 * 
 * @author Kevin
 * @since 2017年5月19日
 */
public class Room implements Cloneable {

    private transient Map<String, IObject> baseObjects = new HashMap<>();// 原始的对象
    private transient boolean isLocked;

    private int id;
    private String name;
    private String desc;

    private Map<Direction, SimpleRoomInfo> exits;
    private Map<String, IObject> objects;

    public Room() {
        exits = new ConcurrentHashMap<>();
        objects = new ConcurrentHashMap<>();
    }

    public Room(int id, String name, String desc) {
        this();
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public boolean addObject(IObject object) {
        if (!hasObject(object.getId())) {
            this.objects.put(object.getId(), object);
            buildResponse();
            return true;
        }
        return false;
    }

    private void buildResponse() {
        for (User user : getUsers()) {
            Response.autoRoom(this, user).ready();
        }
    }

    protected Room clone() {
        try {
            return (Room) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public Room excludeClone(IObject exclude) {
        Room clone = clone();
        if (clone != null) {
            clone.objects = new ConcurrentHashMap<>(this.objects);
            if (clone.hasObject(exclude.getId())) {
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

    public SimpleRoomInfo getExit(Direction direction) {
        return exits.get(direction);
    }

    public IObject getObject(String id) {
        return objects.get(id);
    }

    public List<SimpleObjectInfo> getObjects() {
        List<SimpleObjectInfo> simpleObjectInfos = new ArrayList<>();
        for (IObject object : objects.values()) {
            simpleObjectInfos.add(SimpleObjectInfo.create(object));
        }
        Collections.sort(simpleObjectInfos, new Comparator<SimpleObjectInfo>() {
            @Override
            public int compare(SimpleObjectInfo o1, SimpleObjectInfo o2) {
                return o1.getObjectType().ordinal() - o2.getObjectType().ordinal();
            }
        });
        return simpleObjectInfos;
    }

    @JSONField(serialize = false)
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (IObject object : this.objects.values()) {
            if (object.getType().isUser()) {
                users.add((User) object);
            }
        }
        return users;
    }

    public boolean hasObject(String id) {
        return this.objects.containsKey(id);
    }

    public void initBaseObjects(Set<IObject> baseObjects) {
        if (!this.baseObjects.isEmpty()) {
            throw new MudVerifyException("baseObjects初始化异常");
        }
        for (IObject object : baseObjects) {
            if (object.getType().isUser()) {
                throw new MudVerifyException("baseObjects初始化异常");
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

    public boolean isLocked() {
        return isLocked;
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

    public boolean removeObject(IObject object) {
        if (this.objects.containsKey(object.getId())) {
            this.objects.remove(object.getId());
            buildResponse();
            return true;
        }
        return false;
    }

    public void setDesc(String description) {
        this.desc = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleRoomInfo toSimpleInfo() {
        return new SimpleRoomInfo(getId(), getName());
    }

}
