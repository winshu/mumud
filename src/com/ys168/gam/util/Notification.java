package com.ys168.gam.util;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.model.Room;
import com.ys168.gam.model.User;
import com.ys168.gam.web.websocket.SessionHandler;

/**
 * 通知线程
 * 
 * @author Kevin
 * @since 2017年5月18日
 */
public class Notification implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Notification.class);
    private static LinkedBlockingQueue<Response> readyResponses = new LinkedBlockingQueue<>();
    private SessionHandler sessionHandler;

    private Notification() {
        sessionHandler = new SessionHandler();
    }

    public static void start() {
        Thread t = new Thread(new Notification(), Notification.class.getSimpleName());
        t.setDaemon(true);
        t.start();
        log.debug("启动UserNotification");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Response response = readyResponses.take();
                if (response != null) {
                    sessionHandler.send(response);
                    Thread.sleep(10L);
                }
            }
            catch (InterruptedException e) {
                log.debug("停止UserNotification");
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 将需要发送的消息推至队列
     * 
     * @param response
     * @return
     */
    public static boolean put(Response response) {
        try {
            readyResponses.put(response);
            return true;
        }
        catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public static void putRoomNotification(Room room) {
        if (room == null) {
            return;
        }
        for (User user : room.getUsers()) {
            Response response = Response.autoRoom(room, user);
            Notification.put(response);
        }
    }

}
