package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.model.IObject;
import com.ys168.gam.model.Npc;
import com.ys168.gam.model.Room;

/**
 * 交谈指令
 * 
 * @author Kevin
 * @since 2017年5月22日
 */
@CmdName("ask")
public class Ask extends Cmd {

    private Npc npc;

    public Ask(Context context) {
        super(context);
    }

    @Override
    protected boolean beforeExecute() {
        if (!hasArgument()) {
            return fail("你要跟谁说话？");
        }
        if (context.getRoom() == null) {
            return fail("找不到可以说话的人");
        }
        Room room = context.getRoom();
        IObject object = room.getObject(getArgument());
        if (object == null || !object.getType().isNpc()) {
            return fail("这里没有这个人");
        }
        npc = (Npc) object;

        return true;
    }

    @Override
    protected boolean doExecute() {
        return info(npc.getName() + "打了个哈哈，不鸟你");
    }

}
