package com.ys168.gam.cmd.test;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;

@CmdName("test")
public class Test extends Cmd {

    public Test(Context context) {
        super(context);
    }

    @Override
    public Response doExecute() {
        return Response.info("Test测试成功,参数:<{0}>", getArgument());
    }

    @Override
    protected Response beforeExecute() {
        return null;
    }
}
