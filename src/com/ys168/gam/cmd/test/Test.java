package com.ys168.gam.cmd.test;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;

@CmdName("test")
public class Test extends Cmd {

    public Test(Context context) {
        super(context);
    }

    @Override
    public boolean doExecute() {
        return info("Test测试成功,参数:<{0}>", getArgument());
    }

    @Override
    protected boolean beforeExecute() {
        return true;
    }
}
