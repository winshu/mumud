package com.ys168.gam.cmd;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.exception.MudException;
import com.ys168.gam.util.Notification;

public abstract class Cmd {

    protected static String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final Context context;

    public Cmd(Context context) {
        this.context = context;
    }

    protected abstract boolean beforeExecute();

    protected abstract boolean doExecute();

    public final void execute() {
        if (beforeExecute()) {
            context.getUser().setBusy(true);
            try {
                doExecute();
            }
            catch (MudException e) {
                log.error(e.getMessage(), e);
            }
            finally {
                context.getUser().setBusy(false);
            }
        }
    }

    protected boolean fail(String message, Object... pattern) {
        put(Response.error(message, pattern));
        return false;
    }

    protected boolean info(String message, Object... pattern) {
        put(Response.info(message, pattern));
        return true;
    }

    protected String getArgument() {
        return context.getArgument();
    }

    protected boolean hasArgument() {
        return StringUtils.isNotEmpty(context.getArgument());
    }

    /**
     * 推送消息
     * 
     * @param response
     */
    protected boolean put(Response response) {
        response.addUser(context.getUser());
        Notification.put(response);
        return true;
    }
}
