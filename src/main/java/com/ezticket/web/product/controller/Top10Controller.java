package com.ezticket.web.product.controller;

import com.ezticket.web.product.service.Top10Service;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/Top10Controller")
public class Top10Controller extends HttpServlet {


    private Top10Service top10ServiceSvc;

    public void init() throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        top10ServiceSvc = applicationContext.getBean(Top10Service.class);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");


        if ("top10List".equals(action)) {
            List top10list = top10ServiceSvc.top10List();
            Gson gson = new Gson();
            String json = gson.toJson(top10list);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(json);
            out.flush();
            return;
        }

        if("indexSearch".equals(action)){
            String keyword =request.getParameter("keyword") !=null ? request.getParameter("keyword"): "";
//            System.out.println("search keywords: " + keyword);
            List<Object[]> list =top10ServiceSvc.indexSearch(keyword);
//            System.out.println(list);
            Gson gson = new Gson();
            String json = gson.toJson(list);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(json);
            out.flush();
            return;
        }


    }


}
