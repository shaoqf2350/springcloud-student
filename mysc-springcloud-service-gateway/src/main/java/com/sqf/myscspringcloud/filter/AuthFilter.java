package com.sqf.myscspringcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 网关过滤器,权限验证
 * @Author: shaoqingfeng
 * @CREATE: 2023/12/4 23:18
 */
@Component
public class AuthFilter extends ZuulFilter {

    @Override
    public String filterType(){
        return "pre"; // 开始过滤之前执行
    }

    @Override // 多个过滤器时，执行顺序
    public int filterOrder(){
        return 0;
    }

    /**
     * 判断过滤器是否执行
     * @return
     */
    @Override
    public boolean shouldFilter(){
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        if (token == null) { //token 只要token不为空就是合法请求
            ctx.setSendZuulResponse(false); // false:表示不对该请求进行路由
            ctx.setResponseStatusCode(401); // 设置响应码,401表示无权限访问
            ctx.addZuulResponseHeader("content-type", "text/html;charset=utf-8");
            ctx.setResponseBody("非法访问"); // 设置响应值
        }
        return null;
    }

}
