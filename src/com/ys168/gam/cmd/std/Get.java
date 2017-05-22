package com.ys168.gam.cmd.std;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.model.IObject;
import com.ys168.gam.model.Item;
import com.ys168.gam.model.Room;

/**
 * 获取物品
 * 
 * @author Kevin
 * @since 2017年5月22日
 */
@CmdName("get")
public class Get extends Cmd {

    private Item item;

    public Get(Context context) {
        super(context);
    }

    @Override
    protected boolean beforeExecute() {
        if (!hasArgument()) {
            return fail("你捡个空气啊");
        }
        Room room = context.getRoom();
        if (room == null) {
            return fail("找不到物品");
        }

        IObject object = room.getObject(getArgument());
        if (object == null || !object.getType().isItem()) {
            return fail("这里没有这个物品");
        }

        item = (Item) object;
        return true;
    }

    @Override
    protected boolean doExecute() {
        if (context.getUser().addItem(item)) {
            context.getRoom().removeObject(item);

            Response response = Response.info("#FFFFFF{0}拾起了『{1}』##", context.getUser().getName(), item.getName());
            response.addUser(context.getRoom().getUsers());
            return response.ready();
        }
        return fail("拾取物品失败，请检查背包");
    }

}
