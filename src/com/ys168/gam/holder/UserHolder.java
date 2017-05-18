package com.ys168.gam.holder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.ys168.gam.model.User;

public class UserHolder {

    private static Map<String, User> map = new ConcurrentHashMap<>();

    public static User get(String accountId) {
        return map.get(accountId);
    }

    public static void put(String accountId, User user) {
        map.put(accountId, user);
    }

    public static void remove(String accountId) {
        map.remove(accountId);
    }

    public static User createUser(String accountId, String httpSessionId) {
        User user = new User();
        user.setId("u" + new Random().nextInt(10000));
        user.setName(accountId);
        user.setAccountId(accountId);
        user.setHttpSessionId(httpSessionId);

        return user;
    }

    public static List<User> getAllUser() {
        return new ArrayList<>(map.values());
    }
}
