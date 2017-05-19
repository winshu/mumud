package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;

@CmdName("say")
public class Say extends Cmd {

    public Say(Context context) {
        super(context);
    }

    @Override
    protected boolean doExecute() {
        return info("你说了一些话:{0}", getArgument());
    }

    @Override
    protected boolean beforeExecute() {
        if (!hasArgument()) {
            return fail("你想说什么");
        }
        return true;
    }
}
