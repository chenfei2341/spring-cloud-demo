/*
 * <pre>
 * 标  题: CommonInterceptor.java.
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

package com.znv.peim.northbound.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * 拦截器处理
 * @author ChenFei
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
    /**
     * 请求开始时间
     */
    ThreadLocal<Long> stThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        printParams(request, response, handler);
        // 拦截器排除
        String uri = request.getRequestURI();

        HttpSession session = request.getSession();
        String contextPath = request.getServletContext().getContextPath();

        boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));

        uri = uri.substring(contextPath.length());
        logger.debug("ctxPath:{} \t {} ", contextPath, uri);

        Object user = session.getAttribute("USERINFO");
        //为未登录情况下修改帐号开放"/oldPwdIsRight","/usereditpwd","/editPwd"
        String[] notProtectedUri = {"/login", "/logout", "/verify", "/websocket", "/redirect", "/oldPwdIsRight", "/usereditpwd", "/editPwd",
            "/custom/systeminfo/get", "/auth-consumer/auth"};

        for (String s : notProtectedUri) {
            if (uri.contains(s)) {
                return true;
            }
        }
        return true;
/*
        if (user != null) {
            return true;
        } else {

            if (isAjax) {
                //                JsonResult.CODE_NOT_LOGIN;
                response.setContentType("text/plain;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //                response.getWriter().write(JsonUtil.toJson(result));
            } else {
                response.sendRedirect(contextPath + "/views/redirect");
            }
            return false;
        }*/
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long st = stThreadLocal.get();
        long speedTime = System.currentTimeMillis() - st;
        logger.info("RequestId:{}, 本次请求耗时SpeedTime:{}ms ", st, speedTime);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 异步消息的返回时间
    }

    /**
     * 输出入参等信息
     * @param request request
     * @param response response
     * @param handler handler
     */
    private void printParams(HttpServletRequest request, HttpServletResponse response, Object handler) {

        long st = System.currentTimeMillis();
        stThreadLocal.set(st);

        // 接收到请求，记录请求内容
        StringBuilder sb = new StringBuilder();

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        sb.append("本次请求信息如下:\r\n");
        sb.append("RequestId:" + st + "\r\n");
        sb.append("RequestURL:" + request.getRequestURL().toString() + "\r\n");
        sb.append("RemoteAddr : " + request.getRemoteAddr() + "\r\n");
        sb.append("Method:" + handlerMethod.getBean().getClass().getName() + "." + handlerMethod.getMethod().getName() + "\r\n");
        //获取所有请求参数：
        Enumeration<String> enu = request.getParameterNames();
        sb.append("RequestParams:[");
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            sb.append(paraName + ": " + request.getParameter(paraName));
            if (enu.hasMoreElements()) {
                sb.append(",");
            }
        }
        sb.append("]");

        logger.info(sb.toString());
    }
}