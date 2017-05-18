package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.holder.MapHolder;
import com.ys168.gam.model.Room;

@CmdName("jh")
public class JiangHu extends Cmd {

    public JiangHu(Context context) {
        super(context);
    }

    @Override
    protected Response beforeExecute() {
        return null;
    }

    @Override
    protected Response doExecute() {
        Room room = MapHolder.getStartRoom("huashancun");
        context.getUser().setRoom(room);

        return Response.room(room, context.getUser());
    }

}
