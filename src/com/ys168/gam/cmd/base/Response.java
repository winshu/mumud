package com.ys168.gam.cmd.base;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ys168.gam.model.IObject;
import com.ys168.gam.model.Room;

/**
 * 
 * @author Kevin
 * @since 2017年4月29日
 */
public class Response {

    public static final String KEY_RESPONSE_CODE = "response_code";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_ROOM = "room";

    public static final int GLOBAL_CLOSE_CODE = -10000;

    public static final int SUCCESS_CODE = 10000;
    public static final int ERROR_CODE = 20000;

    public static final Response SESSION_CLOSE = new Response(GLOBAL_CLOSE_CODE, "连接已关闭");
    public static final Response SUCCESS = new Response(SUCCESS_CODE, "成功");

    public static Response error(String message, Object... arguments) {
        Response response = new Response(ERROR_CODE);
        response.put(KEY_MESSAGE, MessageFormat.format(message, arguments));
        return response;
    }

    public static Response info(String message, Object... arguments) {
        Response response = new Response(SUCCESS_CODE);
        response.put(KEY_MESSAGE, MessageFormat.format(message, arguments));
        return response;
    }

    public static Response room(Room room, IObject exclude) {
        Response response = new Response(SUCCESS_CODE);
        response.put(KEY_ROOM, room.excludeClone(exclude));
        return response;
    }

    private boolean isBroadcast;
    private Map<String, Object> map;

    public Response(int code) {
        this.map = new HashMap<>();
        map.put(KEY_RESPONSE_CODE, code);
    }

    public Response(int code, String message) {
        this(code);
        map.put(KEY_MESSAGE, message);
    }

    public Response put(String key, Object value) {
        if (this.map.containsKey(key)) {
            throw new IllegalArgumentException("key contains already");
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
}
