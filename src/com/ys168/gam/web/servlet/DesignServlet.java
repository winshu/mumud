package com.ys168.gam.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.ys168.gam.exception.MudVerifyException;

@WebServlet(name = "designServlet", urlPatterns = { "/design", "/saveDesign" }, loadOnStartup = 1)
public class DesignServlet extends HttpServlet {

    private static final long serialVersionUID = -470771346578146092L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getRequestURI().toString();
        if (method.endsWith("/design")) {
            req.getRequestDispatcher("page/design.html").forward(req, resp);
        }
        if (method.endsWith("/saveDesign")) {
            req.setCharacterEncoding("UTF-8");

            String fileName = req.getParameter("filename");
            String designData = req.getParameter("design");

            if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(designData)) {
                throw new MudVerifyException("非法空参数，请检查");
            }

            String[] fileNameParts = fileName.split("\\.");
            String version = new SimpleDateFormat("MMddHHmm").format(new Date());
            String fullName = fileNameParts[0] + '_' + version + '.' + fileNameParts[1];

            resp.setContentLength(designData.getBytes().length);
            resp.addHeader("Content-Disposition", "attachment; filename=" + fullName.toLowerCase());

            ServletOutputStream servletOutputStream = resp.getOutputStream();

            servletOutputStream.write(designData.getBytes());
            servletOutputStream.flush();
            servletOutputStream.close();
        }
    }
}
