package com.ezticket.web.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezticket.web.product.pojo.Pclass;
import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.service.PimgtService;
import com.ezticket.web.product.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


@WebServlet("/ProductInfoServlet")
public class ProductInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productSvc;
    private PimgtService pimgtSvc;

    @Override
    public void init() throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        productSvc = applicationContext.getBean(ProductService.class);
        pimgtSvc = applicationContext.getBean(PimgtService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        //前台僅顯示上架商品
        if ("availableProductList".equals(action)) {
            Map<String, String[]> map = new HashMap<>();
            String pstatus[] = {"0"};  //0是已上架，1是已下架
            map.put("pstatus", pstatus);
            List<Product> allAvailableProductlist = productSvc.getAllByproductSearch(map);
            list2json(allAvailableProductlist, response);
            return;
        }
        if ("singleProduct".equals(action)) {
            Integer productno = Integer.valueOf(request.getParameter("productno"));
            Product product = productSvc.getOneProduct(productno);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String ujson = gson.toJson(product);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(ujson);
            pw.flush();
            return;
        }

        if ("allProductList".equals(action)) {
            List<Product> allAvailableProductlist = productSvc.getAllProduct();
            list2json(allAvailableProductlist, response);
            return;
        }






        //商品複合查詢
        if ("ProductSearchForm".equals(action)) {
            Map<String, String[]> map = request.getParameterMap(); //將得到的資料轉成map
            System.out.println("我有跑進來ProductSearchForm" + map);
            List<Product> productList = productSvc.getAllByproductSearch(map); //轉交進行複合查詢
            list2json(productList, response);
        }
    }

    public void list2json(List<Product> productList, HttpServletResponse response) throws IOException {
        //轉換成json格式寫出
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = gson.toJson(productList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
    }

}
