package com.ys168.gam.websocket;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.model.User;

/**
 * 
 * @author Kevin
 * @since 2017年4月12日
 */
@ApplicationScoped
public class SessionHandler {

    private static final Logger log = LoggerFactory.getLogger(SessionHandler.class);
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    void addSession(String httpSessionId, Session session) {
        sessions.put(httpSessionId, session);
        send(session, Response.info("当前在线人数:" + sessions.size()));
    }

    /**
     * 强制广播给指定用户
     * 
     * @param users
     * @param response
     */
    public void broadcast(Collection<User> users, Response response) {
        for (User user : response.getUsers()) {
            Session s = sessions.get(user.getHttpSessionId());
            doSend(s, response);
        }
    }

    /**
     * 强制全局广播
     * 
     * @param response
     */
    public void broadcast(Response response) {
        for (Session s : sessions.values()) {
            doSend(s, response);
        }
    }

    void closeSession(String httpSessionId) {
        Session session = sessions.get(httpSessionId);
        if (session == null) {
            return;
        }
        try {
            if (session.isOpen()) {
                doSend(session, new Response(Response.GLOBAL_CLOSE_CODE, "连接已关闭"));
            }
            session.close();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private boolean doSend(Session session, Response response) {
        if (session == null || !session.isOpen()) {
            return false;
        }
        try {
            session.getBasicRemote().sendText(response.toJson());
            return true;
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    void removeSession(String httpSessionId) {
        sessions.remove(httpSessionId);
    }

    /**
     * 发送消息，优先级：全局>指定用户>单点发送
     * 
     * @param session
     * @param response
     */
    public void send(Session session, Response response) {
        if (response.isBroadcast()) {
            broadcast(response);
            return;
        }
        if (!response.getUsers().isEmpty()) {
            broadcast(response.getUsers(), response);
            return;
        }
        if (session != null) {
            doSend(session, response);
        }
    }

    /**
     * @param httpSessionId
     * @param response
     * @see #send(Session, Response)
     */
    public void send(String httpSessionId, Response response) {
        Session session = sessions.get(httpSessionId);
        send(session, response);
    }

}
