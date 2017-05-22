package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.holder.MapHolder;
import com.ys168.gam.model.AreaMap;

/**
 * 
 * @author Kevin
 * @since 2017年5月22日
 */
@CmdName("jh")
public class JiangHu extends Cmd {
    
    private AreaMap map;

    public JiangHu(Context context) {
        super(context);
    }

    @Override
    protected boolean beforeExecute() {
        String argument = getArgument().isEmpty() ? "test" : getArgument();
        map = MapHolder.getMap(argument);
        return map == null ? fail("找不到这个地图") : true;
    }

    @Override
    protected boolean doExecute() {
        info("你雇了一驾马车，风尘仆仆地朝“{0}”赶去！", map.getName());

        context.getUser().changeRoom(map.getStartRoom());
        info("一路劳顿，你终于来到了“{0}”！", map.getName());

        return true;
    }
}
