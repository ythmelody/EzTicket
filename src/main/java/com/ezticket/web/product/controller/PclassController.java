package com.ezticket.web.product.controller;

import com.ezticket.web.product.pojo.Pclass;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.service.PclassService;
import com.ezticket.web.product.service.PcommentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


@WebServlet("/PclassController")
public class PclassController extends HttpServlet {

    private PclassService pclassSvc;

    @Override
    public void init() throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        pclassSvc = applicationContext.getBean(PclassService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        //取得所有類別
        if ("productClassList".equals(action)) {
            List<Pclass> pclassList = pclassSvc.getAllProductClass();
            list2json(pclassList, resp);
        }

        //新增商品類別
        if ("addProductClass".equals(action)) {
            String pclassname = req.getParameter("pclassname");
            Pclass pclass = pclassSvc.addProductClass(pclassname);
            Gson gson = new Gson();
            String json = gson.toJson(pclass);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pw = resp.getWriter();
            pw.print(json);
            pw.flush();
        }

        //取得單筆商品類別
        if ("getOneProductClass".equals(action)) {
            Integer pclassno = Integer.valueOf(req.getParameter("pclassno"));
            Pclass pclass = pclassSvc.getOneProductClass(pclassno);
            Gson gson = new Gson();
            String json = gson.toJson(pclass);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pw = resp.getWriter();
            pw.print(json);
            pw.flush();
        }


        if ("updateOneproductClass".equals(action)) {
            Integer pclassno = Integer.valueOf(req.getParameter("pclassno"));
            String pclassname = req.getParameter("pclassname_new");
            Pclass pclass = pclassSvc.updateProductClass(pclassno, pclassname);
            Gson gson = new Gson();
            String json = gson.toJson(pclass);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pw = resp.getWriter();
            pw.print(json);
            pw.flush();
        }

        //查詢類別名稱及確認新增&修改類別是否重複名稱
        if ("pclassnameComfirm".equals(action)) {
            String pclassname = req.getParameter("pclassname");
            List<Pclass> pclassList = pclassSvc.comfiremIfRepeated(pclassname);
            list2json(pclassList, resp);
        }

        if ("pclassnameSearch".equals(action)) {
            String pclassname = req.getParameter("pclassname");
            List<Pclass> pclassList = pclassSvc.getAllByPclassNameSearch(pclassname);
            list2json(pclassList, resp);
        }
    }



    public void list2json(List<Pclass> pclassList, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(pclassList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.print(json);
        pw.flush();
    }
}
