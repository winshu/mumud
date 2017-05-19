package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.model.IObject;
import com.ys168.gam.model.Role;
import com.ys168.gam.model.Room;

@CmdName("look")
public class Look extends Cmd {

    private IObject object;

    public Look(Context context) {
        super(context);
    }

    @Override
    protected Response beforeExecute() {
        if (!hasArgument()) {
            fail("你的四周灰蒙蒙地一片，什么也没有。");
        }
        String argument = getArgument();
        Room room = context.getRoom();
        this.object = room.getObject(argument);

        if (this.object == null) {
            fail("你的周国没有这个人/物");
        }

        return null;
    }

    @Override
    protected Response doExecute() {
        if (object.getType().isUser()) {// 返回用户信息
            return Response.role((Role) object);
        }
        if (object.getType().isNpc()) {
            return Response.role((Role) object);
        }

        return fail("查看失败");
    }

}
