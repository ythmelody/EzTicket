package com.ezticket.core.config;

import com.ezticket.web.users.dto.RoleDTO;
import com.ezticket.web.users.pojo.Backuser;
import com.ezticket.web.users.pojo.Roleauthority;
import com.ezticket.web.users.service.BackuserService;
import com.ezticket.web.users.service.RoleauthorityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {


    @Autowired
    BackuserService backuserService;
    @Autowired
    RoleauthorityService roleauthorityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("backuser") == null) {
            response.sendRedirect("/front-users-bu-sign-in.html"); // 未登入的話,導回登入頁面
            return false;
        }else {
//            Backuser backuser = (Backuser) session.getAttribute("backuser");
//            //還沒想好要怎麼攔截權限
//            String baaccount = backuser.getBaaccount();
//            Backuser orignbackuser = backuserService.getBackuserInfo(baaccount);
//            List<Integer> baAllFuncno = roleauthorityService.getAllfuncno(orignbackuser.getBaroleno());
//
//            //Map 裝 funcno  :  html
//            Map<Integer, String> htmlMap = new HashMap<>();
//            htmlMap.put(1, "back-index.html"); //後台首頁
//            htmlMap.put(2, "back-users-profile.html"); //後台使用者資訊管理
//            htmlMap.put(3, "back-users-member.html"); // 會員資訊管理
//            htmlMap.put(4, ""); //常見問題管理
//            htmlMap.put(5, ""); //聊天室交談
//            htmlMap.put(6, ""); //驗票
//            htmlMap.put(7, ""); //系統參數管理
//            htmlMap.put(9, "back-users-my-team.html"); //後台帳號及權限管理
//            htmlMap.put(10, ""); //最新消息管理
//            htmlMap.put(11, ""); //票券優惠券管理
//            htmlMap.put(12, ""); //商品優惠券管理
//            htmlMap.put(13, ""); //帳務管理
//            htmlMap.put(14, "back-users-host.html"); //主辦單位管理
//            htmlMap.put(15, ""); //商品訂單管理
//            htmlMap.put(16, "back-product-comment_manager.html"); //商品評論管理
//            htmlMap.put(17, "back-product-product_manage.html"); //商品管理
//            htmlMap.put(18, ""); //票券訂單管理
//            htmlMap.put(19, ""); //節目管理
//            htmlMap.put(20, ""); //節目評論管理
//            htmlMap.put(21, ""); //場地座位管理
//            htmlMap.put(22, ""); //場地模板座位管理
//            htmlMap.put(23, ""); //節目區域及票價管理
//
//            //拿到點選進入的網址
//            String requestURI = request.getRequestURI();
//            int index = requestURI.lastIndexOf("/");
//            String htmlName = requestURI.substring(index + 1);
//            Integer funcno = null;
//            for (Map.Entry<Integer, String> entry : htmlMap.entrySet()) {
//                if (entry.getValue().equals(htmlName)) {
//                    funcno = entry.getKey();
//                    break;
//                }
//            }
//
//            if (funcno == null || !baAllFuncno.contains(funcno)) {
//                session.setAttribute("showAlert", "您沒有權限訪問此頁面。");
//                response.sendRedirect(request.getContextPath() + "/back-index.html");
//                return false;
//            }
            return true;
        }
    }
}
