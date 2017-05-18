package com.ys168.gam.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ys168.gam.constant.Constant;

@WebFilter(filterName = "userFilter", urlPatterns = { "/*" })
public class UserFilter implements Filter {

    private static final String[] excludePaths = { "/login", "/register", "/design", "/saveDesign"};

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (isExcludePath(request) || request.getSession().getAttribute(Constant.ACCOUNT_ID) != null) {
            filterChain.doFilter(req, resp);
        }
        else {
            ((HttpServletResponse) resp).sendRedirect(request.getContextPath());
        }
    }

    private boolean isExcludePath(HttpServletRequest request) {
        for (String path : excludePaths) {
            if (request.getServletPath().equals(path)) {
                return true;
            }
        }
        return request.getServletPath().indexOf(".") > -1;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

}
