package com.ys168.gam.cmd.adm;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.holder.MapHolder;
import com.ys168.gam.model.AreaMap;
import com.ys168.gam.model.Room;

/**
 * 管理员命令，允许飞行
 * 
 * @author Kevin
 * @since 2017年5月19日
 */
@CmdName("fly")
public class Fly extends Cmd {

    private Room nextRoom;
    private String mapName;

    public Fly(Context context) {
        super(context);
    }

    @Override
    protected boolean beforeExecute() {
        if (!hasArgument()) {
            return fail("你想飞到哪儿去？");
        }
        if (context.getUser().isBusy()) {
            return fail("你的动作还没有完成，不能移动。");
        }

        int roomId = parseInt(getArgument());
        if (roomId < 0) {
            return fail("你这是要上天啊？");
        }

        AreaMap map = MapHolder.getMap(roomId);
        if (map == null) {
            return fail("你这是要上天啊？");
        }

        mapName = map.getName();
        nextRoom = map.getRoom(roomId);

        return true;
    }

    @Override
    protected boolean doExecute() {
        context.getUser().changeRoom(nextRoom);
        info("一道白光闪过，你来到了“{0}-{1}”！", mapName, nextRoom.getName());

        return true;
    }

}
