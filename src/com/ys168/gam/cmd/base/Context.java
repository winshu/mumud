package com.ys168.gam.cmd.base;

import com.ys168.gam.model.Room;
import com.ys168.gam.model.User;

/**
 * 
 * @author Kevin
 * @since 2017年4月26日
 */
public class Context {

    private User user;
    private String cmdName;
    private String argument;

    public String getArgument() {
        return argument;
    }

    public String getCmdName() {
        return cmdName;
    }

    public Room getRoom() {
        return user == null ? null : user.getRoom();
    }

    public User getUser() {
        return user;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public void setCmdName(String cmd) {
        this.cmdName = cmd;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
