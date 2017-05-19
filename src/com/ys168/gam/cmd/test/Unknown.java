package com.ys168.gam.cmd.test;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;

public class Unknown extends Cmd {

    public Unknown(Context context) {
        super(context);
    }

    @Override
    public boolean doExecute() {
        put(Response.info("什么"));
        return true;
    }

    @Override
    protected boolean beforeExecute() {
        return true;
    }
}
