package com.ys168.gam.web.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys168.gam.cmd.base.CmdLoader;
import com.ys168.gam.cmd.base.Notification;
import com.ys168.gam.holder.MapHolder;

/**
 * 
 * @author Kevin
 * @since 2017年5月17日
 */
@WebListener("initListener")
public class InitListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(InitListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {}

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        log.debug("开始初始化数据=======>");
        long begin = System.currentTimeMillis();

        CmdLoader.scan();
        MapHolder.initMap();
        Notification.start();

        long end = System.currentTimeMillis();
        log.debug("完成初始化数据=======>耗时:" + (end - begin) / 1000D);
    }
}
