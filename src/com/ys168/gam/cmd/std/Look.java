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
    protected boolean beforeExecute() {
        if (!hasArgument()) {
            return fail("你的四周灰蒙蒙地一片，什么也没有。");
        }
        String argument = getArgument();
        Room room = context.getRoom();
        this.object = room.getObject(argument);

        if (this.object == null) {
            return fail("你的周围没有这个人/物");
        }

        return true;
    }

    @Override
    protected boolean doExecute() {
        if (object.getType().isUser()) {// 返回用户信息
            return put(Response.role((Role) object));
        }
        if (object.getType().isNpc()) {
            return put(Response.role((Role) object));
        }
        return fail("查看失败");
    }

}
