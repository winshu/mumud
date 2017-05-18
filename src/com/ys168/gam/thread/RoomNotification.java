package com.ys168.gam.thread;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.holder.MapHolder;
import com.ys168.gam.model.Room;
import com.ys168.gam.model.User;
import com.ys168.gam.websocket.SessionHandler;

/**
 * 通知线程
 * 
 * @author Kevin
 * @since 2017年5月18日
 */
public class RoomNotification implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RoomNotification.class);

    @Inject
    private SessionHandler sessionHandler;

    private RoomNotification() {
        sessionHandler = new SessionHandler();
    }

    public static void start() {
        Thread t = new Thread(new RoomNotification(), RoomNotification.class.getSimpleName());
        t.setDaemon(true);
        t.start();
        log.debug("启动UserNotification");
    }

    @Override
    public void run() {
        while (notification()) {
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException e) {
                log.debug("停止UserNotification");
                log.error(e.getMessage(), e);
            }
        }
    }

    private boolean notification() {
        for (Room room : MapHolder.getAllRoom()) {
            if (!room.isNeedRefresh()) {
                continue;
            }
            for (User user : room.getUsers()) {
                Response response = Response.room(room, user);
                sessionHandler.send(user.getHttpSessionId(), response);
            }
            room.refreshFinished();
        }
        return true;
    }

}
