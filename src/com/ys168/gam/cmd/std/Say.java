package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;

@CmdName("say")
public class Say extends Cmd {

    public Say(Context context) {
        super(context);
    }

    @Override
    protected Response doExecute() {
        return Response.info("你说了一些话:{0}", getArgument());
    }

    @Override
    protected Response beforeExecute() {
        if (!hasArgument()) {
            return Response.error("你想说什么");
        }
        return null;
    }
}
