package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.model.IObject;

@CmdName("look")
public class Look extends Cmd {

    private IObject object;

    public Look(Context context) {
        super(context);
    }

    @Override
    protected Response beforeExecute() {
        if (!hasArgument()) {
            return Response.error("你在看什么？");
        }
        String argument = getArgument();

        return null;
    }

    @Override
    protected Response doExecute() {
        String message = lookRoom();
        return Response.info(message);
    }

    private String lookRoom() {
        return "你的四周灰蒙蒙地一片，什么也没有。";
    }

}
