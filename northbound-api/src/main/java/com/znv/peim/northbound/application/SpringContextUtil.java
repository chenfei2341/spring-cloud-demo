/**
 * <pre>
 * 标  题: SpringContextUtil.java.
 * 版权所有: 版权所有(C)2001-2019
 * 公   司: 深圳中兴力维技术有限公司
 * 内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 其他说明: // 其它内容的说明
 * 完成日期: 2019年9月2日
 * </pre>
 * <pre>
 * 修改记录1:
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * @version 1.0
 * @author ChenFei
 */
package com.znv.peim.northbound.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author ChenFei
 */
public class SpringContextUtil implements ApplicationContextAware {
    /**
     * spring容器上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 把ApplicationContext对象inject到当前类中作为一个静态成员变量。
     * @param applicationContextParam
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContextParam) {
        if (null == SpringContextUtil.applicationContext) {
            SpringContextUtil.applicationContext = applicationContextParam;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据beanName获取一个bean对象
     * @param name beanName
     * @return 返回一个bean对象
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据类获取一个bean对象
     * @param clazz 类类型
     * @return 返回一个bean对象
     */
    public static Object getBean(Class<?> clazz) {
        return applicationContext.getBean(clazz);
    }
}