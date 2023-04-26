package com.ezticket.web.product.controller;


import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.PdetailsPK;
import com.ezticket.web.product.service.PcommentService;
import com.ezticket.web.product.service.PdetailsService;
import com.ezticket.web.product.service.ProductService;
import com.ezticket.web.product.util.PageResult;
import com.ezticket.web.users.pojo.Backuser;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.service.BackuserService;
import com.ezticket.web.users.service.MemberService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/ProductCommentServlet")
public class ProductCommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PcommentService pcommentSvc;
    private ProductService productSvc;
    private PdetailsService pdetailSvc;
    private PdetailsPK pdetailsPK;
    private MemberService memberSvc;
    private BackuserService backuserSvc;
    Member newMember;

    Backuser newbackuser;

    @Override
    public void init() throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        pcommentSvc = applicationContext.getBean(PcommentService.class);
        productSvc = applicationContext.getBean(ProductService.class);
        pdetailSvc = applicationContext.getBean(PdetailsService.class);
        pdetailsPK = applicationContext.getBean(PdetailsPK.class);
        memberSvc = applicationContext.getBean(MemberService.class);
        backuserSvc = applicationContext.getBean(BackuserService.class);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String action = request.getParameter("action");
        //從請求得到session
        HttpSession session = request.getSession();
        //確認是否為登入狀態
        Boolean isMember = false;
        Boolean isAdmin = false;
        Member member = (Member) session.getAttribute("member");
        Backuser backuser = (Backuser) session.getAttribute("backuser");
        if (member != null) {
            isMember = true;
            newMember = memberSvc.getMemberInfo(member.getMemail());
        }
        if (backuser != null) {
            isAdmin = true;
            newbackuser = backuserSvc.getBackuserInfo(backuser.getBaaccount());
        }

        //建立一個使用Bean Validation的validator實例
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();


        //取得所有商品評論
        if ("productCommentList".equals(action)) {
            List<Pcomment> pcommentList = pcommentSvc.getAllProductComment();
            list2json(pcommentList, response);
        }

        //取得單一商品所有評論(用於前端商品單一詳情)
        if ("oneProductCommentList".equals(action)) {
            Map<String, String[]> map = new HashMap<>();
            String productno[] = {request.getParameter("productno")};
            map.put("productno", productno);
            String pcommentstatus[] = {"0"};  //僅狀態顯示於前台的商品
            map.put("pcommentstatus", pcommentstatus);
            List<Pcomment> pcommentList = pcommentSvc.getAllBySearch(map);
            //避免新增商品評論時注入攻擊(XSS)
            for (Pcomment pcomment : pcommentList) {
                String rawCommentCont = pcomment.getPcommentcont();
//                System.out.println("raw string: "+rawCommentCont);
                String escapedHtml = StringEscapeUtils.escapeHtml4(rawCommentCont);
//                System.out.println("safe string: "+escapedHtml);
                pcomment.setPcommentcont(escapedHtml);
            }
            list2json(pcommentList, response);
        }

        //新增商品評論 (同時更新商品總評星) --> 會員綁定才可以新增
        if ("addProductComment".equals(action)) {
            if (!isMember) {
                response.sendRedirect("front-users-mem-sign-in.html");
                return;
            }
            //取得會員編號
            Integer memberno = newMember.getMemberno();
            Integer productno = request.getParameter("productno").length() != 0 ? Integer.valueOf(request.getParameter("productno")) : null;
            Integer prate = request.getParameter("prate").length() != 0 ? Integer.valueOf(request.getParameter("prate")) : null;
            String pcommentcont = request.getParameter("pcommentcont");


            //建立一個POJO,並設定屬性值
            Pcomment pc = new Pcomment();
            pc.setMemberno(memberno);
            pc.setProductno(productno);
            pc.setPrate(prate);
            pc.setPcommentcont(pcommentcont);

            //驗證POJO是否符合限制
            Set<ConstraintViolation<Pcomment>> violations = validator.validate(pc);
            if (!violations.isEmpty()) {
                //如果有錯誤就回傳錯誤訊息
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                Map map = new HashMap();
                for (ConstraintViolation<Pcomment> violation : violations) {
                    System.out.println(violation);
                    map.put(violation.getPropertyPath(), violation.getMessage());
                }
                Gson gson = new Gson();
                String json = gson.toJson(map);
                PrintWriter pw = response.getWriter();
                pw.print(json);
                pw.flush();
                return;
            }
            //處理正常請求
            Pcomment pcomment = pcommentSvc.addProductComment(productno, pcommentcont, prate, memberno);

            //同時更新商品總評星
            productSvc.updateProduct(productno, prate);

            //同時更新訂單明細評論狀態
            Integer porderno = Integer.valueOf(request.getParameter("porderno"));
            pdetailsPK.setPorderno(porderno);
            pdetailsPK.setProductno(productno);
            //用怪方法存進pcommentno(記得要去改SQL型別)
            pdetailSvc.updateByID(pdetailsPK, pcomment.getPcommentno()); //0是未評論，其餘數字是評論編號

            Gson gson = new Gson();
            String json = gson.toJson(pcomment);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();
        }

        //取得單一筆評論 --> 會員綁定才可以查看 (查看已評論的才會跳出會員登入驗證，如果是還沒有評論要加上去的話要改pdtailController)
        if ("getOneproductComment".equals(action)) {
            if (!isMember && !isAdmin) {
                response.sendRedirect("front-users-mem-sign-in.html");
                return;
            }
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
            Boolean updateOK = pcommentSvc.updateProductComment(pcommentno, pcommentstatus);
            Gson gson = new Gson();
            String json = gson.toJson(updateOK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();
        }

        //評論複合查詢
        if ("CommentSearch".equals(action)) {
            Map<String, String[]> map = request.getParameterMap(); //將得到的資料轉成map
            List<Pcomment> commentList = pcommentSvc.getAllBySearch(map); //轉交進行複合查詢
            list2json(commentList, response);
        }

        //取得會員按讚的商品編號(判斷會員是否按讚依據) --> 會員綁定才可以看的到，但不強制登入
        if ("getThumpupPcommentno".equals(action)) {
            if (!isMember) {
                return;
            }
            //用session取得會員編號
            Integer memberno = newMember.getMemberno();
            Set<Integer> set = pcommentSvc.getPcommentnosByMemberno(memberno);
            Gson gson = new Gson();
            String json = gson.toJson(set);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();


        }

        //評論按讚　--> 會員綁定才可以點讚，用swal詢問是否導到登入畫面
        if ("thumpupPcomment".equals(action)) {
            if (!isMember) {
                Map map = new HashMap();
                map.put("status", 302);
                map.put("location", "front-users-mem-sign-in.html");
                Gson gson = new Gson();
                String json = gson.toJson(map);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.print(json);
                pw.flush();
                return;
            }
            //用session取得會員編號
            Integer memberno = newMember.getMemberno();
            Integer pcommentno = Integer.valueOf(request.getParameter("pcommentno"));
            Boolean thumpup = pcommentSvc.addThumpUp(memberno, pcommentno);
            Gson gson = new Gson();
            String json = gson.toJson(thumpup);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();
            return;
        }

        //評論取消按讚 --> 會員綁定才可以取消點讚，用swal詢問是否導到登入畫面
        if ("thumpdownPcomment".equals(action)) {
            if (!isMember) {
                Map map = new HashMap();
                map.put("status", 302);
                map.put("location", "front-users-mem-sign-in.html");
                Gson gson = new Gson();
                String json = gson.toJson(map);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.print(json);
                pw.flush();
                return;
            }
            //用session取得會員編號
            Integer memberno = newMember.getMemberno();
            Integer pcommentno = Integer.valueOf(request.getParameter("pcommentno"));
            Boolean thumpdown = pcommentSvc.removeThumpUp(memberno, pcommentno);
            Gson gson = new Gson();
            String json = gson.toJson(thumpdown);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();
            return;
        }
        //評論複合查詢搭配分頁(後台商品管理打這支)
        if ("CommentSearchPage".equals(action)) {
            Map<String, String[]> map = request.getParameterMap();
            Integer pageNumebr = Integer.valueOf(request.getParameter("pageNumber"));
            Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
            PageResult<Pcomment> pcommentList = pcommentSvc.getAllBySearch(map, pageNumebr, pageSize);
            List<Pcomment> rawdata = pcommentList.getData();
            for (Pcomment pcomment : rawdata) {
                String rawCommentCont = pcomment.getPcommentcont();
                String escapedHtml = StringEscapeUtils.escapeHtml4(rawCommentCont);
                pcomment.setPcommentcont(escapedHtml);
            }
            Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            String json = gson.toJson(pcommentList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        }

        //提供客人更改評論內容 --> 會員綁定才可以修改評論
        if ("updateOneproductComment".equals(action)) {
            if (!isMember) {
                response.sendRedirect("front-users-mem-sign-in.html");
                return;
            }
            Integer pcommentno = request.getParameter("pcommentno").length() != 0 ? Integer.valueOf(request.getParameter("pcommentno")) : null;
            Integer prate = request.getParameter("prate").length() != 0 ? Integer.valueOf(request.getParameter("prate")) : null;
            String pcommentcont = request.getParameter("pcommentcont");

            //建立一個POJO,並設定屬性值
            Pcomment pc = pcommentSvc.getOneProductComment(pcommentno);
            pc.setPrate(prate);
            pc.setPcommentcont(pcommentcont);

            //驗證POJO是否符合限制
            Set<ConstraintViolation<Pcomment>> violations = validator.validate(pc);
            if (!violations.isEmpty()) {
                //如果有錯誤就回傳錯誤訊息
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                Map map = new HashMap();
                for (ConstraintViolation<Pcomment> violation : violations) {
                    System.out.println(violation);
                    map.put(violation.getPropertyPath(), violation.getMessage());
                }
                Gson gson = new Gson();
                String json = gson.toJson(map);
                PrintWriter pw = response.getWriter();
                pw.print(json);
                pw.flush();
                return;
            }

            //正常的話執行以下程式碼
            Boolean updateOK = pcommentSvc.updateProductComment(pcommentno, prate, pcommentcont);
            Gson gson = new Gson();
            String json = gson.toJson(updateOK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();
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
