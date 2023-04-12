package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.pojo.TicketHolder;
import com.ezticket.web.activity.service.CollectService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class CollectController extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String collectnoStr = req.getParameter("collectno");
        Integer collectno = Integer.valueOf(collectnoStr);
        String membernoStr = req.getParameter("memberno");
        Integer memberno = Integer.valueOf(membernoStr);

        if ("getOne_For_Display".equals(action)) {

//          查詢資料
            CollectService coSvc = new CollectService();
            TicketHolder ticketHolder = coSvc.getOneTicket(collectno);
            req.setAttribute("ticketHolder", ticketHolder); // 資料庫取出的ticketHolder物件,存入req
            String url = "/resources/static/front-activity-ticket-detail.html";
            RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 front-activity-ticket-detail.html
            successView.forward(req, res);
        }

        if ("getAll_For_Display".equals(action)) {

//          查詢資料
            CollectService coSvc = new CollectService();
            List<TicketHolder> list = coSvc.getByMemberno(memberno);
            req.setAttribute("list", list);
            String url = "/resources/static/front-activity-ticket-holder.html";
            RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 front-activity-ticket-detail.html
            successView.forward(req, res);
        }
    }
}
