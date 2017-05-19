package com.ys168.gam.cmd.base;

import java.util.HashSet;
import java.util.Set;

public class CmdArgumentFilter {

    private static final Set<String> SENSITIVES = new HashSet<>();
    static {
        SENSITIVES.add("[<>]+");
    }

    public static String filter(String argument) {
        for (String string : SENSITIVES) {
            argument = argument.replaceAll(string, "*");
        }
        return argument;
    }
}
