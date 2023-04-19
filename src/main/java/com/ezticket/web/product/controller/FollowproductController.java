package com.ezticket.web.product.controller;

import com.ezticket.web.product.pojo.Followproduct;
import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.repository.FollowproductDAO;
import com.ezticket.web.product.service.*;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.service.MemberService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/FollowproductController")
public class FollowproductController extends HttpServlet {

    private FollowproductService followproductSvc;

    private Top10 top10Svc;

    private MemberService memberSvc;

    Member newMember;

    public void init() throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        followproductSvc = applicationContext.getBean(FollowproductService.class);
        memberSvc = applicationContext.getBean(MemberService.class);
        top10Svc = applicationContext.getBean(Top10.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Boolean isMember = false;
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            isMember = true;
            newMember = memberSvc.getMemberInfo(member.getMemail());
        }

        if ("getFollowProductList".equals(action)) {
            if (!isMember) {
                return;
            }
            Integer memberno = newMember.getMemberno();
            List<Followproduct> followproductList = followproductSvc.getFollowProductByMemberno(memberno);
            Gson gson = new Gson();
            String json = gson.toJson(followproductList);
            PrintWriter out = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out.println(json);
            out.flush();
            return;
        }


        if ("unfollowOneProduct".equals(action)) {
            if (!isMember) {
                response.sendRedirect("front-users-mem-sign-in.html");
                return;
            }
            Integer memberno = newMember.getMemberno();
            Integer productno = Integer.valueOf(request.getParameter("productno"));
            Boolean unfollowOneProductOK = followproductSvc.deleteOneProductFollowing(memberno, productno);
            Gson gson = new Gson();
            String json = gson.toJson(unfollowOneProductOK);
            PrintWriter out = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out.println(json);
            out.flush();
            return;
        }

        if ("addOneProductFollow".equals(action)) {
            if (!isMember) {
                response.sendRedirect("front-users-mem-sign-in.html");
                return;
            }
            Integer memberno = newMember.getMemberno();
            Integer productno = Integer.valueOf(request.getParameter("productno"));
            Boolean successful = followproductSvc.addFollowProduct(memberno, productno);
            boolean2Json(successful, response);
        }

        if ("unfollowAllproduct".equals(action)) {
            if (!isMember) {
                response.sendRedirect("front-users-mem-sign-in.html");
                return;
            }
            Integer memberno = newMember.getMemberno();
            Boolean unfollowAllProductOK = followproductSvc.deleteFollowProductByMemberno(memberno);
            Gson gson = new Gson();
            String json = gson.toJson(unfollowAllProductOK);
            PrintWriter out = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out.println(json);
            out.flush();
            return;
        }
    }

    public void boolean2Json(Boolean successful, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(successful);
        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        out.println(json);
        out.flush();
    }

}
