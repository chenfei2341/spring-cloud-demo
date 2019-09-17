/*
 * <pre>
 * 标  题: AppContextListener.java.
 * 版权所有: 版权所有(C)2001-2019
 * 公   司: 深圳中兴力维技术有限公司
 * 内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 其他说明: // 其它内容的说明
 * 完成日期: 2019-09-02 // 输入完成日期
 * </pre>
 * <pre>
 * 修改记录1:
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * @version 1.0
 * @author Chenfei
 */

package com.znv.peim.northbound.common.listener;

import com.znv.peim.northbound.application.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * webapp 初始化
 */
@WebListener
public class AppContextListener implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            logger.info("|======== ZNV_PEIM NorthBound AppContext is initialized. =========|");
            logger.info("        ****************************************        ");
            logger.info("        ****************************************        ");
            logger.info("        ****                                 ***        ");
            logger.info("        ****      ###NorthBound Server###      ***        ");
            logger.info("        ****                                 ***        ");
            logger.info("        ****************************************        ");
            logger.info("        ****************************************        ");

            System.getProperties().setProperty("mail.mime.splitlongparameters", "false");

            ServletContext servletContext = sce.getServletContext();
            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            SpringContextUtil springUtil = new SpringContextUtil();
            springUtil.setApplicationContext(context);

        } catch (Exception e) {
            logger.error("#### AppContextListener error. System exit...", e);
            System.exit(0);
        }
    }

    /**
     * 热部署
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("#### ZNV_PEIM NorthBound ServletContext is destroyed.");
        try {
            logger.debug("----- Now, Finalize hot deploy [NorthBound Server] start -----");
            logger.info("#####Finalize [NorthBound Server] start ...");
            // 关闭定时器线程
            logger.info("##### Stopping timer util ...");

            destroyJDBCDrivers();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            logger.info("##### Finalize hot deploy [NorthBound Server] end. OK!!! ");
        }
    }

    private void destroyJDBCDrivers() {
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver;
        while (drivers.hasMoreElements()) {
            driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("Deregister JDBC driver %s successful", driver));
            } catch (SQLException e) {
                logger.error(String.format("Deregister JDBC driver %s error", driver), e);
            }
        }
    }

}