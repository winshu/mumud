package com.ys168.gam.cmd.base;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys168.gam.web.websocket.SessionHandler;

/**
 * 通知线程
 * 
 * @author Kevin
 * @since 2017年5月18日
 */
public class Notification implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Notification.class);
    private static final long INTERVAL = 10L;
    private static LinkedBlockingQueue<Response> readyResponses = new LinkedBlockingQueue<>();

    /**
     * 将需要发送的消息推至队列
     * 
     * @param response
     * @return
     */
    public static boolean put(Response response) {
        try {
            if (!readyResponses.contains(response)) {
                readyResponses.put(response);
            }
            return true;
        }
        catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public static void start() {
        Thread t = new Thread(new Notification(), Notification.class.getSimpleName());
        t.setDaemon(true);
        t.start();
        log.debug("启动UserNotification");
    }

    private SessionHandler sessionHandler = new SessionHandler();

    private Notification() {}

    @Override
    public void run() {
        while (true) {
            try {
                Response response = readyResponses.take();
                if (response != null) {
                    sessionHandler.send(response);
                    Thread.sleep(INTERVAL);
                }
            }
            catch (InterruptedException e) {
                log.debug("停止UserNotification");
                log.error(e.getMessage(), e);
            }
        }
    }

}
