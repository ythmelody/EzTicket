package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.pojo.CollectVO;
import com.ezticket.web.activity.pojo.TicketHolder;
import com.ezticket.web.activity.repository.CollectDAO;
import com.ezticket.web.activity.repository.impl.CollectDAOImpl;
import com.ezticket.web.activity.service.CollectService;
import com.ezticket.web.product.pojo.PdetailsPK;
import com.ezticket.web.product.service.PcommentService;
import com.ezticket.web.product.service.PdetailsService;
import com.ezticket.web.product.service.ProductService;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import com.ezticket.web.users.service.MemberService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/collect")
public class CollectController extends HttpServlet {

//    =========== JDBC 抓不到 Spring MVC 的 MemberRepository，分票改用 CollectCrudController
//    @Autowired
//    private MemberService memberService;
//
//    public void init() throws ServletException {
//        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//        memberService = applicationContext.getBean(MemberService.class);
//    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
//        System.out.println(action);



        if ("myCollect".equals(action)) {
//            暫時沒用，由queryString轉交變數
//            String collectnoStr = req.getParameter("collectno");
//            Integer collectno = Integer.valueOf(collectnoStr);
//
////          查詢資料
//            CollectService coSvc = new CollectService();
//            TicketHolder ticketHolder = coSvc.getOneTicket(collectno);
//            String membernoStr = req.getParameter("memberno");

//            Integer memberno = Integer.valueOf(membernoStr);
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

//            String membernoStr = req.getParameter("memberno");
//            Integer memberno = Integer.valueOf(membernoStr);
            String url = "front-activity-ticket-detail.html";
            RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 front-activity-ticket-detail.html
            successView.forward(req, res);
        }

//        分票
//        if ("transferTicket".equals(action)) {
//
//            String collectnoStr = req.getParameter("collectno");
//            String memail = req.getParameter("memail");
//            Integer collectno = Integer.valueOf(collectnoStr);


//          更新擁有者
//            CollectService coSvc = new CollectService();
//            CollectDAO cdao = new CollectDAOImpl();
// Step1:  用 email 查出會員編號
//        取不到memberRepository......
//        Member member = memberRepository.findByMemail(memail);
//        Member member = findByMemail(memail);
//
//            String stat;
//            Member member = memberService.getMemberInfo(memail);
//            if (member == null) {
//                stat = "查無此會員";
//            } else {
//                Integer newMemberno = member.getMemberno();

// Step2: 更新 collect 表格中的會員編號
//                CollectVO oldCollectVO = cdao.findByPK(collectno);
//                CollectVO collectVO = new CollectVO();
//                collectVO.setCollectno(collectno);
//                collectVO.setMemberno(memberno);
//                collectVO.settDetailsno(oldCollectVO.gettDetailsno());
//                collectVO.settStatus(oldCollectVO.gettStatus());
//                collectVO.setQrcode(oldCollectVO.getQrcode());
//                cdao.update(collectVO);
//                stat = "分票成功！";
//            }
//            res.setContentType("text/HTML;charset=UTF-8");
//            PrintWriter pw = res.getWriter();
//            pw.write(stat);
//            pw.flush();
//        }

//        票夾渲染
        if ("allMyTickets".equals(action)) {
            String membernoStr = req.getParameter("memberno");
//            System.out.println(membernoStr);
            if (membernoStr == null){
                return;
            }
            Integer memberno = Integer.valueOf(membernoStr);
//            System.out.println(memberno);

//          查詢資料
            CollectService coSvc = new CollectService();
            List<TicketHolder> list = coSvc.getByMemberno(memberno);
//            System.out.println(list);
            if (list == null) {
                return;
            }
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String json = gson.toJson(list);
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = res.getWriter();
            pw.write(json);
            pw.flush();
        }

//      單張畫面渲染
        if ("ticketDetails".equals(action)) {
            String membernoStr = req.getParameter("memberno");
            System.out.println(membernoStr);
            if (membernoStr == null){
                System.out.println("卡membernoStr");
                return;
            }
            Integer memberno = Integer.valueOf(membernoStr);
            String collectnoStr = req.getParameter("collectno");
            Integer collectno = Integer.valueOf(collectnoStr);

//          查詢資料
            CollectService coSvc = new CollectService();
            TicketHolder ticketHolder = coSvc.getOneTicket(collectno);
            System.out.println(ticketHolder.getMemberno());
//            如果此張票券擁有者不是此會員，回票夾
            if (!memberno.equals(ticketHolder.getMemberno())) {
                System.out.println("票券不是他的");
//                用 fetch 跳轉沒效
//                res.sendRedirect("front-activity-ticket-holder.html");
                return;
            }
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String json = gson.toJson(ticketHolder);
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = res.getWriter();
            pw.write(json);
            pw.flush();
        }

        if ("getImg".equals(action)) {

            String collectnoStr = req.getParameter("collectno");
            if (collectnoStr == null) {
                return;
            }
            Integer collectno = Integer.valueOf(collectnoStr);

//          查詢資料
            CollectService coSvc = new CollectService();
            TicketHolder ticketHolder = coSvc.getOneTicket(collectno);
            byte[] img = ticketHolder.getAimg();

//            String base64Image = coSvc.blobToBase64(img);
//            res.setContentType("text/html;charset=UTF-8");
//            PrintWriter out = res.getWriter();
//            out.print(base64Image);
//            out.println("<img src=\"data:image/png;base64," + base64Image + "\"/>");

            res.setContentType("image/jpeg");

            if (img != null) {
                try (InputStream inputStream = new ByteArrayInputStream(img);
                     OutputStream out = res.getOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/images/event-imgs/qmark.jpg");
                     OutputStream out = res.getOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
