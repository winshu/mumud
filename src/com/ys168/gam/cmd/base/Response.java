package com.ys168.gam.cmd.base;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ys168.gam.model.IObject;
import com.ys168.gam.model.Role;
import com.ys168.gam.model.Room;
import com.ys168.gam.model.User;
import com.ys168.gam.util.MudVerifyException;

/**
 * 
 * @author Kevin
 * @since 2017年4月29日
 */
public class Response {

    public static final String KEY_RESPONSE_CODE = "response_code";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_ROOM = "room";
    public static final String KEY_ROLE = "role";

    public static final int GLOBAL_CLOSE_CODE = 99999;

    public static final int DEFAULT_SUCCESS_CODE = 10000;
    public static final int DEFAULT_ERROR_CODE = 100001;
    public static final int AUTO_ROOM_CODE = 20000;
    public static final int MESSAGE_CODE = 20001;
    public static final int ROOM_CODE = 20002;
    public static final int ROLE_CODE = 20003;

    public static Response error(String message, Object... arguments) {
        Response response = new Response(DEFAULT_ERROR_CODE);
        response.put(KEY_MESSAGE, MessageFormat.format(message, arguments));
        return response;
    }

    public static Response info(String message, Object... arguments) {
        Response response = new Response(MESSAGE_CODE);
        response.put(KEY_MESSAGE, MessageFormat.format(message, arguments));
        return response;
    }

    public static Response room(Room room, IObject exclude) {
        Response response = new Response(ROOM_CODE);
        response.put(KEY_ROOM, room.excludeClone(exclude));
        return response;
    }

    public static Response autoRoom(Room room, IObject exclude) {
        Response response = new Response(AUTO_ROOM_CODE);
        response.put(KEY_ROOM, room.excludeClone(exclude));
        return response;
    }

    public static Response role(Role role) {
        Response response = new Response(ROLE_CODE);
        response.put(KEY_ROLE, role.toSimpleInfo());
        return response;
    }

    private boolean isBroadcast;
    private Collection<User> users;// 需要通知的用户
    private Map<String, Object> map;

    public Response(int code) {
        this.map = new HashMap<>();
        this.users = new HashSet<>();
        map.put(KEY_RESPONSE_CODE, code);
    }

    public Response(int code, String message) {
        this(code);
        map.put(KEY_MESSAGE, message);
    }

    public Response put(String key, Object value) {
        if (this.map.containsKey(key)) {
            throw new MudVerifyException("key contains already");
        }
        this.map.put(key, value);
        return this;
    }

    public String toJson() {
        return JSON.toJSONString(map);
    }

    public boolean isBroadcast() {
        return isBroadcast;
    }

    public void setBroadcast(boolean isBroadcast) {
        this.isBroadcast = isBroadcast;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public Response addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Response setUser(Collection<User> users) {
        this.users.clear();
        this.users.addAll(users);
        return this;
    }
}
