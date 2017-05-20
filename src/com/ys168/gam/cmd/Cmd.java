package com.ys168.gam.cmd;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.exception.MudException;

/**
 * 
 * @author Kevin
 * @since 2017年5月21日
 */
public abstract class Cmd {

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

    protected int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e) {
            return -1;
        }
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
        return response.addUser(context.getUser()).ready();
    }

}
