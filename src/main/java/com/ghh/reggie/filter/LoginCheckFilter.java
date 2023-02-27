package com.ghh.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.ghh.reggie.common.BaseContext;
import com.ghh.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("本次请求的URI：{}",requestURI);
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        //判断请求是否要处理
        boolean check = check(urls,requestURI);


        //如果不需要处理，直接放行
        if(check){
            log.info("uri不需要处理，直接放行");
            filterChain.doFilter(request,response);
            return;
        }

        //判断登录状态，如果已经登录，也放行
        if(request.getSession().getAttribute("employee") != null){
            log.info("已登录id：{}，放行", request.getSession().getAttribute("employee"));
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }

        //如果未登录，通过输出流方式向客户端页面响应数据
        log.info("未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//        filterChain.doFilter(request,response);
        return;
    }

    /**
     * 判断uri是否需要处理方法
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url,requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
