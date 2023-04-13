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

        if ("oneTicket".equals(action)) {
            String collectnoStr = req.getParameter("collectno");
            Integer collectno = Integer.valueOf(collectnoStr);

//          查詢資料
            CollectService coSvc = new CollectService();
            TicketHolder ticketHolder = coSvc.getOneTicket(collectno);
            req.setAttribute("ticketHolder", ticketHolder); // 資料庫取出的ticketHolder物件,存入req
            String url = "front-activity-ticket-detail.html";
            RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 front-activity-ticket-detail.html
            successView.forward(req, res);
        }

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

//            forward 方法
//            req.setAttribute("list", list);
//            String url = "front-activity-ticket-holder.html";
//            RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 front-activity-ticket-detail.html
//            successView.forward(req, res);
        }
    }
}
