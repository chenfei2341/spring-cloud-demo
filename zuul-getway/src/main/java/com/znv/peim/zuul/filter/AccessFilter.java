package com.znv.peim.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AccessFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String accessToken = request.getParameter("accessToken");
        if (StringUtils.isNotBlank(accessToken)) {
			// 对该请求进行路由
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
			// 设值，让下一个Filter看到上一个Filter的状态
            ctx.set("isSuccess", true);
            return null;
        } else {
			// 过滤该请求，不对其进行路由
            ctx.setSendZuulResponse(false);
			// 返回错误码
            ctx.setResponseStatusCode(401);
			// 返回错误内容
            ctx.setResponseBody("{\"result\":\"accessToken is not correct!\"}");
            ctx.set("isSuccess", false);
            return null;
        }
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
