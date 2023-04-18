package com.ezticket.web.product.controller;


import com.ezticket.web.product.pojo.Preport;
import com.ezticket.web.product.service.PreportService;
import com.ezticket.web.product.util.PageResult;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.service.MemberService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.NewMemberClassTypeMunger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/ProductCommentReportServlet")
public class ProductCommentReport extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PreportService preportSvc;

    Member newMember;
    private MemberService memberSvc;

    @Override
    public void init() throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        preportSvc = applicationContext.getBean(PreportService.class);
        memberSvc = applicationContext.getBean(MemberService.class);

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        //從請求得到session
        HttpSession session = request.getSession();
        //確認是否為登入狀態
        Boolean loggedin =(Boolean)session.getAttribute("loggedin");
        //如果是登入狀態得到member拿來用
        if(loggedin != null && loggedin == true){
            //這邊的member只有email跟password(沒有memberno)
            Member member =(Member) session.getAttribute("member");
            //取得所有member pojo資訊(包含memberno)
            newMember =memberSvc.getMemberInfo(member.getMemail());

        }

        //取得所有商品評論檢舉
        if ("productCommentRepostList".equals(action)) {
            List<Preport> preportList = preportSvc.getAllProductReport();
            list2json(preportList,response);
        }

        //取得單一筆商品評論檢舉(後台的方法)
        if ("productOneCommentRepost".equals(action)) {
            Integer preportno = Integer.valueOf(request.getParameter("preportno"));
            Preport preport = preportSvc.getOneProductReport(preportno);
            Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            String json = gson.toJson(preport);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();
        }
        //更新單一筆商品評論檢舉(後台的方法)
        if ("updateOneproductCommentReportStatus".equals(action)) {
            Integer preportno = Integer.valueOf(request.getParameter("preportno"));
            Integer preportstatus = Integer.valueOf(request.getParameter("preportstatus"));
            Boolean updateOK = preportSvc.updateProductReport(preportno, preportstatus);
            Gson gson = new Gson();
            String json = gson.toJson(updateOK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();

        }
        //複合查詢(現在被複合查詢+分頁取代)
        if ("CommentReportSearch".equals(action)) {
            Map<String, String[]> map = request.getParameterMap(); //將得到的資料轉成map
            System.out.println("CommentReportSearch" + map);
            List<Preport> commentReportList = preportSvc.getAllBySearch(map); //轉交進行複合查詢
            list2json(commentReportList, response);
        }

        //新增評論檢舉-(前台)商品詳情評論輪播
        if ("addProductCommentReport".equals(action)) {
            if(loggedin ==null || loggedin ==false){
                response.sendRedirect("front-users-mem-sign-in.html");
                return;
            }
            Integer memberno = newMember.getMemberno();
            Integer pcommentno = Integer.valueOf(request.getParameter("pcommentno"));
            String pwhy = request.getParameter("pwhy");
            Preport preport = preportSvc.addProductReport(pcommentno, memberno, pwhy);
            Gson gson = new Gson();
            String json =gson.toJson(preport);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
            return;
        }

        if ("CommentReportSearchPage".equals(action)) {
            Map<String, String[]> map = request.getParameterMap(); //將得到的資料轉成map
            Integer pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
            Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
            PageResult<Preport> commentReportList = preportSvc.getAllBySearch(map,pageNumber,pageSize); //轉交進行複合查詢
            Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            String json = gson.toJson(commentReportList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        }


    }

    public void list2json(List pcommentList, HttpServletResponse response) throws IOException {
        //轉換成json格式寫出
        Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
        String json = gson.toJson(pcommentList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
    }

}
