package com.ys168.gam.websocket;

import java.text.MessageFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdFactory;
import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.constant.Constant;
import com.ys168.gam.holder.UserHolder;
import com.ys168.gam.model.User;

/**
 * 处理发送过来的命令
 * 
 * @author Kevin
 * @version 2017年4月12日
 */
@ApplicationScoped
@ServerEndpoint(value = "/actions", configurator = HttpSessionConfigurator.class)
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    @Inject
    private SessionHandler sessionHandler;

    private String getHttpSessionId(Session session) {
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        return httpSession == null ? null : httpSession.getId();
    }

    private String getAccountId(Session session) {
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        try {
            return httpSession == null ? null : (String) httpSession.getAttribute(Constant.ACCOUNT_ID);
        }
        catch (IllegalStateException e) {
            sessionHandler.closeSession(httpSession.getId());
            return null;
        }
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        String accountId = getAccountId(session);
        User user = UserHolder.get(accountId);

        Cmd cmd = CmdFactory.create(user, message);
        Response response = cmd.execute();
        sessionHandler.send(session, response);
    }

    @OnClose
    public void onClose(Session session) {
        String accountId = getAccountId(session);
        String httpSessionId = getHttpSessionId(session);
        sessionHandler.removeSession(httpSessionId);

        log.debug(MessageFormat.format("Close session, accountId={0},sessionid={1},httpSessionId={2}", accountId, session.getId(), httpSessionId));
    }

    @OnError
    public void onError(Throwable error) {
        log.error(error.getMessage(), error);
    }

    @OnOpen
    public void onOpen(Session session) {
        String accountId = getAccountId(session);
        String httpSessionId = getHttpSessionId(session);

        // 检查
        User user = UserHolder.get(accountId);
        if (user == null) {
            user = UserHolder.createUser(accountId, httpSessionId);
        }
        else {
            sessionHandler.closeSession(user.getHttpSessionId());
            user.setHttpSessionId(httpSessionId);
        }
        UserHolder.put(accountId, user);

        sessionHandler.closeSession(httpSessionId);
        sessionHandler.addSession(httpSessionId, session);

        log.debug(MessageFormat.format("Open session, accountId={0},sessionid={1},httpSessionId={2}", accountId, session.getId(), httpSessionId));
    }
}
