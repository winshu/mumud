package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;

/**
 * 
 * @author Kevin
 * @since 2017年5月21日
 */
@CmdName("say")
public class Say extends Cmd {

    public Say(Context context) {
        super(context);
    }

    @Override
    protected boolean beforeExecute() {
        if (!hasArgument()) {
            return fail("你想说什么");
        }
        if (context.getRoom() == null) {
            return fail("你都没出去，说个P呀");
        }
        return true;
    }

    @Override
    protected boolean doExecute() {
        Response response = Response.info("{0}: {1}", context.getUser().getName(), getArgument());
        response.addUser(context.getRoom().getUsers());
        return response.ready();
    }
}
