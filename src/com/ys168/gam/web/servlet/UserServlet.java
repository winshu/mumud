package com.ys168.gam.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ys168.gam.constant.Constant;
import com.ys168.gam.holder.UserHolder;

@WebServlet(name = "userServlet", urlPatterns = { "/login", "/register", "/main" }, loadOnStartup = 1)
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 5504416830352557874L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getRequestURI().toString();
        if (method.endsWith("/login")) {
            login(req, resp);
        }
        if (method.endsWith("/register")) {
            register(req, resp);
        }
        if (method.endsWith("/main")) {
            req.getRequestDispatcher("page/main.html").forward(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountId = req.getParameter("userid");
        String password = req.getParameter("password");

        if (UserHolder.validate(accountId, password)) {
            req.getSession().setAttribute(Constant.ACCOUNT_ID, req.getParameter("userid"));
            resp.sendRedirect("main?id=" + accountId);
        }
        else {
            resp.sendRedirect(req.getContextPath());
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }
}
