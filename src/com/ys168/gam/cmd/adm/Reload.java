package com.ys168.gam.cmd.adm;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.holder.MapHolder;
import com.ys168.gam.holder.UserHolder;
import com.ys168.gam.model.Room;
import com.ys168.gam.model.User;

/**
 * 重新加载Map
 * 
 * @author Kevin
 * @since 2017年5月20日
 */
@CmdName("reload")
public class Reload extends Cmd {

    public Reload(Context context) {
        super(context);
    }

    @Override
    protected boolean beforeExecute() {
        
        return true;
    }

    @Override
    protected boolean doExecute() {
        MapHolder.initMap();

        for (User u : UserHolder.getAllUser()) {
            Room room = u.getRoom();
            if (room != null) {
                Room newRoom = MapHolder.getRoom(room.getId());
                u.changeRoom(newRoom);
            }
        }

        return info("<font color='#ff0000'>重新加载地图完毕</font>");
    }

}
