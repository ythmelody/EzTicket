package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.pojo.TicketHolder;
import com.ezticket.web.activity.service.CollectService;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
@WebServlet("/collect")
public class CollectController extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String membernoStr = req.getParameter("memberno");
        Integer memberno = Integer.valueOf(membernoStr);

        if ("myCollect".equals(action)) {
//            暫時沒用，由queryString轉交變數
//            String collectnoStr = req.getParameter("collectno");
//            Integer collectno = Integer.valueOf(collectnoStr);
//
////          查詢資料
//            CollectService coSvc = new CollectService();
//            TicketHolder ticketHolder = coSvc.getOneTicket(collectno);
            String url = "front-activity-ticket-holder.html";
            RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 front-activity-ticket-detail.html
            successView.forward(req, res);
        }

//      單張票券詳情
        if ("oneTicket".equals(action)) {
//            暫時沒用，由queryString轉交變數
//            String collectnoStr = req.getParameter("collectno");
//            Integer collectno = Integer.valueOf(collectnoStr);
//
////          查詢資料
//            CollectService coSvc = new CollectService();
//            TicketHolder ticketHolder = coSvc.getOneTicket(collectno);
            String url = "front-activity-ticket-detail.html";
            RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 front-activity-ticket-detail.html
            successView.forward(req, res);
        }

//        票夾渲染
        if ("allMyTickets".equals(action)) {

//          查詢資料
            CollectService coSvc = new CollectService();
            List<TicketHolder> list = coSvc.getByMemberno(memberno);
            if (list == null) {
                return;
            }
            Gson gson = new Gson();
            String json = gson.toJson(list);
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = res.getWriter();
            pw.write(json);
            pw.flush();
        }

//      單張畫面渲染
        if ("ticketDetails".equals(action)) {
            String collectnoStr = req.getParameter("collectno");
            Integer collectno = Integer.valueOf(collectnoStr);

//          查詢資料
            CollectService coSvc = new CollectService();
            TicketHolder ticketHolder = coSvc.getOneTicket(collectno);
            Gson gson = new Gson();
            String json = gson.toJson(ticketHolder);
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = res.getWriter();
            pw.write(json);
            pw.flush();
        }
    }
}
