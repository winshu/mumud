package com.ys168.gam.cmd.base;

import com.ys168.gam.cmd.Cmd;
import com.ys168.gam.cmd.test.Unknown;
import com.ys168.gam.model.User;

/**
 * 
 * @author Kevin
 * @since 2017年4月26日
 */
public class CmdFactory {

    public static Cmd create(User user, String input) {
        input = input.trim();
        Context context = new Context();
        context.setCmdName(splitCmd(input));
        context.setArgument(splitArgument(input));
        context.setUser(user);

        return createCmd(context);
    }

    private static Cmd createCmd(Context context) {
        Class<? extends Cmd> cmd = CmdLoader.getCmd(context.getCmdName());
        try {
            return cmd.getConstructor(Context.class).newInstance(context);
        }
        catch (Exception e) {}
        return createUnknownCmd(context);
    }

    private static Unknown createUnknownCmd(Context context) {
        return new Unknown(context);
    }

    private static String splitArgument(String input) {
        int index = input.indexOf(" ");
        if (index == -1) {
            return "";
        }
        String argument = input.substring(index + 1);
        return CmdArgumentFilter.filter(argument);
    }

    private static String splitCmd(String input) {
        int index = input.indexOf(" ");
        if (index == -1) {
            return input;
        }
        return input.substring(0, index);
    }
}
