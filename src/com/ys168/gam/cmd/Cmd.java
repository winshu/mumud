package com.ys168.gam.cmd;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;

public abstract class Cmd {

    protected static String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final Context context;

    public Cmd(Context context) {
        this.context = context;
    }

    protected abstract Response beforeExecute();

    protected abstract Response doExecute();

    public final Response execute() {
        Response validateResult = beforeExecute();
        if (validateResult != null) {
            return validateResult;
        }

        context.getUser().setBusy(true);
        Response response = doExecute();
        context.getUser().setBusy(false);

        return response;
    }

    protected Response fail(String message, Object... pattern) {
        return Response.error(message, pattern);
    }

    protected String getArgument() {
        return context.getArgument();
    }

    protected boolean hasArgument() {
        return StringUtils.isNotEmpty(context.getArgument());
    }
}
