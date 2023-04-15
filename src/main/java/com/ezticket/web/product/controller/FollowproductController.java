package com.ezticket.web.product.controller;

import com.ezticket.web.product.pojo.Followproduct;
import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.repository.FollowproductDAO;
import com.ezticket.web.product.service.FollowproductService;
import com.ezticket.web.product.service.PclassService;
import com.ezticket.web.product.service.PimgtService;
import com.ezticket.web.product.service.ProductService;
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

@WebServlet("/FollowproductController")
public class FollowproductController extends HttpServlet {

    private FollowproductService followproductSvc;

    public void init() throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        followproductSvc = applicationContext.getBean(FollowproductService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("getFollowProductList".equals(action)) {
            Integer memberno = Integer.valueOf(request.getParameter("memberno"));
            List<Followproduct> followproductList = followproductSvc.getFollowProductByMemberno(memberno);
            Gson gson =new Gson();
            String json =gson.toJson(followproductList);
            PrintWriter out = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out.println(json);
            out.flush();
            return;
        }


        if ("unfollowOneProduct".equals(action)) {
            Integer memberno = Integer.valueOf(request.getParameter("memberno"));
            Integer productno = Integer.valueOf(request.getParameter("productno"));
            Boolean unfollowOneProductOK = followproductSvc.deleteOneProductFollowing(memberno,productno);
            Gson gson =new Gson();
            String json =gson.toJson(unfollowOneProductOK);
            PrintWriter out = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out.println(json);
            out.flush();
            return;
        }

        if ("addOneProductFollow".equals(action)){
            Integer memberno = Integer.valueOf(request.getParameter("memberno"));
            Integer productno = Integer.valueOf(request.getParameter("productno"));
            Boolean successful = followproductSvc.addFollowProduct(memberno,productno);
            boolean2Json(successful,response);


        }


    }

    public void boolean2Json(Boolean successful, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        String json =gson.toJson(successful);
        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        out.println(json);
        out.flush();
    }

}
