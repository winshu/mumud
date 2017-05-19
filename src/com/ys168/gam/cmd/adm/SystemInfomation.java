package com.ys168.gam.cmd.adm;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.base.CmdName;
import com.ys168.gam.cmd.base.Context;
import com.ys168.gam.cmd.base.Response;

/**
 * 发送系统消息
 * 
 * @author Kevin
 * @since 2017年5月1日
 */
@CmdName("sys")
public class SystemInfomation extends Cmd {

    public SystemInfomation(Context context) {
        super(context);
    }

    @Override
    protected boolean beforeExecute() {
        if (!hasArgument()) {
            return fail("你在瞎说些什么");
        }
        return true;
    }

    @Override
    protected boolean doExecute() {
        Response response = Response.info("[{0}]:{1}", context.getUser().getName(), getArgument());
        response.setBroadcast(true);
        return put(response);
    }

}
