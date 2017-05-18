package com.ys168.gam.cmd.std;

import java.text.MessageFormat;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;
import com.ys168.gam.constant.Direction;
import com.ys168.gam.holder.MapHolder;
import com.ys168.gam.model.Room;
import com.ys168.gam.model.RoomInfo;

/**
 * 
 * @author Kevin
 * @since 2017年4月26日
 */
@CmdName("go")
public class Go extends Cmd {

    private Direction direction;
    private Room nextRoom;

    public Go(Context context) {
        super(context);
    }

    @Override
    protected Response beforeExecute() {
        if (!hasArgument()) {
            return fail("你要往哪个方向走？");
        }
        if (context.getUser().isBusy()) {
            return fail("你的动作还没有完成，不能移动。");
        }
        if (context.getRoom() == null) {
            return fail("你待在家还想怎样？");
        }

        String arg0 = getArgument();
        String[] args = arg0.split("\\.");

        direction = Direction.get(args[0]);
        if (direction == null) {
            return fail("找不到这个方向。");
        }

        int roomId = -1;
        try {
            roomId = args.length > 1 ? Integer.parseInt(args[1]) : context.getRoom().getId();
        }
        catch (NumberFormatException e) {
            roomId = context.getRoom().getId();
        }

        Room current = MapHolder.getRoom(roomId);
        RoomInfo exit = current.getNextRoom(direction);
        if (exit == null) {
            return fail("这儿没有这个方向");
        }

        nextRoom = MapHolder.getRoom(exit.getId());
        return null;
    }

    @Override
    protected Response doExecute() {
        context.getUser().setRoom(nextRoom);
        Response response = Response.room(nextRoom, context.getUser());
        response.put(Response.KEY_MESSAGE, MessageFormat.format("往{0}走了一步。", direction._NAME));
        return response;
    }
}
