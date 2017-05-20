package com.ys168.gam.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.ys168.gam.model.Room;
import com.ys168.gam.model.User;

public class UserHolder {

    private static Map<String, User> map = new ConcurrentHashMap<>();
    private static Map<String, String> accounts = new HashMap<>();

    static {
        accounts.put("test", "1");
        accounts.put("hello", "1");
        accounts.put("haha", "1");
    }

    public static void backRoom(String accountId, String newHttpSessionId) {
        User user = get(accountId);
        if (user == null) {
            return;
        }

        user.setHttpSessionId(newHttpSessionId);
        Room room = user.getRoom();
        if (room != null) {
            room.addObject(user);
        }
    }

    public static User get(String accountId) {
        return map.get(accountId);
    }

    public static List<User> getAllUser() {
        return new ArrayList<>(map.values());
    }

    public static void leaveRoom(String accountId) {
        User user = get(accountId);
        if (user != null) {
            Room room = user.getRoom();
            if (room != null) {
                room.removeObject(user);
            }
        }
    }

    /**
     * 加载用户
     * 
     * @param accountId
     * @return
     */
    public static String load(String accountId) {
        User user = get(accountId);

        if (user == null) {
            user = new User();
            user.setId("u" + new Random().nextInt(10000));
            user.setName(accountId);
            user.setAccountId(accountId);

            put(accountId, user);
        }
        return user.getHttpSessionId();
    }

    public static void put(String accountId, User user) {
        map.put(accountId, user);
    }

    public static void refreshHttpSessionId(String accountId, String httpSessionId) {
        User user = get(accountId);
        if (user != null) {
            user.setHttpSessionId(httpSessionId);
        }
    }

    public static void remove(String accountId) {
        map.remove(accountId);
    }

    /**
     * 验证登录
     */
    public static boolean validate(String accountId, String password) {
        String pwd = accounts.get(accountId);
        if (pwd == null) {
            return false;
        }
        return pwd.equals(password);
    }

}
