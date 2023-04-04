package com.ezticket.web.product.controller;


import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.service.PcommentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ProductCommentServlet")
public class ProductCommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PcommentService pcommentSvc;

    @Override
    public void init() throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        pcommentSvc = applicationContext.getBean(PcommentService.class);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //取得所有商品評論
        String action = request.getParameter("action");
        if ("productCommentList".equals(action)) {
            List<Pcomment> pcommentList = pcommentSvc.getAllProductComment();
            list2json(pcommentList, response);

        }

        //取得單一商品所有評論(用於前端商品單一詳情)
        if ("oneProductCommentList".equals(action)) {
            Map<String,String[]> map =new HashMap<>();
            String productno[] = {request.getParameter("productno")};
            map.put("productno", productno);
            String pcommentstatus[] = {"0"};  //僅狀態顯示於前台的商品
            map.put("pcommentstatus", pcommentstatus);
            List<Pcomment> pcommentList = pcommentSvc.getAllBySearch(map);
            list2json(pcommentList, response);
        }

        //新增商品評論
        if ("addProductComment".equals(action)) {
            Integer productno = Integer.valueOf(request.getParameter("productno"));
            Integer memberno = Integer.valueOf(request.getParameter("memberno"));
            Integer prate = Integer.valueOf(request.getParameter("prate"));
            String pcommentcont = request.getParameter("pcommentcont");
            pcommentSvc.addProductComment(productno, pcommentcont, prate, memberno);
        }

        //取得單一筆評論
        if ("getOneproductComment".equals(action)) {
            Integer pcommentno = Integer.valueOf(request.getParameter("pcommentno"));
            Pcomment pcomment = pcommentSvc.getOneProductComment(pcommentno);
            Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            String json = gson.toJson(pcomment);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();
        }

        //更新單一評論狀態
        if ("updateOneproductCommentStatus".equals(action)) {
            Integer pcommentno = Integer.valueOf(request.getParameter("pcommentno"));
            Integer pcommentstatus = Integer.valueOf(request.getParameter("pcommentstatus"));
            Boolean updateOK =pcommentSvc.updateProductComment(pcommentno, pcommentstatus);
            Gson gson = new Gson();
            String json = gson.toJson(updateOK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();
        }

        //商品複合查詢
        if ("CommentSearch".equals(action)) {
            Map<String, String[]> map = request.getParameterMap(); //將得到的資料轉成map
            System.out.println("我有跑進來CommentSearch" + map);
            List<Pcomment> commentList = pcommentSvc.getAllBySearch(map); //轉交進行複合查詢
            list2json(commentList, response);
        }
    }

    public void list2json(List<Pcomment> pcommentList, HttpServletResponse response) throws IOException {
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
