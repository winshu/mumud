package com.ys168.gam.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys168.gam.cmd.base.Response;

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
        broadcast(Response.info("当前在线人数:" + sessions.size()));
    }

    public void broadcast(Response response) {
        for (Session session : sessions.values()) {
            if (session.isOpen()) {
                send(session, response);
            }
        }
    }

    public boolean send(String httpSessionId, Response response) {
        Session session = sessions.get(httpSessionId);
        return send(session, response);
    }

    void closeSession(String httpSessionId) {
        Session session = sessions.get(httpSessionId);
        if (session == null) {
            return;
        }
        try {
            if (session.isOpen()) {
                send(session, Response.SESSION_CLOSE);
            }
            session.close();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    void removeSession(String httpSessionId) {
        sessions.remove(httpSessionId);
    }

    boolean send(Session session, Response response) {
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

}
