package com.ys168.gam.cmd.base;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys168.gam.cmd.Cmd;

/**
 * 
 * @author Kevin
 * @since 2017年4月26日
 */
public class CmdLoader {

    private static final Logger log = LoggerFactory.getLogger(CmdLoader.class);

    private static final String BASE_CMD_PACKAGE = Cmd.class.getPackage().getName();
    private static final String BASE_CMD_PATH = Cmd.class.getClassLoader().getResource("").getPath() + Cmd.class.getPackage().getName().replace(".", "/");
    private static Map<String, Class<? extends Cmd>> cmds = new HashMap<>();

    private static final FileFilter classFileFilter = new FileFilter() {
        @Override
        public boolean accept(File pathName) {
            return pathName.exists() && (pathName.isDirectory() || pathName.getName().endsWith(".class"));
        }
    };

    public static Class<? extends Cmd> getCmd(String cmdName) {
        return cmds.get(cmdName);
    }

    @SuppressWarnings("unchecked")
    private static void loadCmdClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (Cmd.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                putCmdClass((Class<? extends Cmd>) clazz);
            }
        }
        catch (ClassNotFoundException e) {
            log.error("加载Cmd类失败:" + className, e);
        }
    }

    private static void putCmdClass(Class<? extends Cmd> clazz) {
        CmdName cmdName = clazz.getAnnotation(CmdName.class);
        if (cmdName == null) {
            return;
        }
        for (String name : cmdName.value()) {
            if (cmds.containsKey(name)) {
                throw new IllegalArgumentException(MessageFormat.format("命令{0}重复定义:{1},{2}", name, clazz.getName(), cmds.get(name).getName()));
            }
            cmds.put(name, clazz);
        }
    }

    public static void scan() {
        scan(new File(BASE_CMD_PATH), BASE_CMD_PACKAGE);
        log.debug("扫描到Cmd个数:" + cmds.size());
    }

    private static void scan(File file, String packageName) {
        if (file.isDirectory()) {
            for (File f : file.listFiles(classFileFilter)) {
                scan(f, (f.isDirectory() ? packageName + "." + f.getName() : packageName));
            }
        }
        if (file.isFile()) {
            String classSimpleName = file.getName().substring(0, file.getName().length() - 6);
            String className = packageName + "." + classSimpleName;
            loadCmdClass(className);
        }
    }
}
