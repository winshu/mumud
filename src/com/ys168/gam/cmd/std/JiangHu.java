package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.holder.MapHolder;
import com.ys168.gam.model.AreaMap;

@CmdName("jh")
public class JiangHu extends Cmd {

    public JiangHu(Context context) {
        super(context);
    }

    @Override
    protected boolean beforeExecute() {
        return true;
    }

    @Override
    protected boolean doExecute() {
        AreaMap areaMap = MapHolder.getMap("huashancun");
        info("你雇了一驾马车，风尘仆仆地朝“{0}”赶去！", areaMap.getName());

        context.getUser().changeRoom(areaMap.getStartRoom());
        info("一路劳顿，你终于来到了“{0}”！", areaMap.getName());

        return true;
    }
}
