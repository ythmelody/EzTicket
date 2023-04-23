package com.ezticket.core.config;

import com.ezticket.web.users.pojo.Backuser;
import com.ezticket.web.users.service.BackuserService;
import com.ezticket.web.users.service.RoleauthorityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
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
        } else {
            Backuser backuser = (Backuser) session.getAttribute("backuser");
            //還沒想好要怎麼攔截權限
            String baaccount = backuser.getBaaccount();
            Backuser orignbackuser = backuserService.getBackuserInfo(baaccount);
            List<Integer> baAllFuncno = roleauthorityService.getAllfuncno(orignbackuser.getBaroleno());

            //Map 裝 funcno  :  html
            Map<Integer, List<String>> htmlMap = new HashMap<>();
            htmlMap.put(1, Arrays.asList("back-index.html")); //後台首頁
            htmlMap.put(2, Arrays.asList("back-users-profile.html")); //後台使用者資訊管理
            htmlMap.put(3, Arrays.asList("back-users-member.html")); // 會員資訊管理
//            htmlMap.put(4, Arrays.asList("")); //常見問題管理
//            htmlMap.put(5, Arrays.asList("")); //客服即時通
            htmlMap.put(6, Arrays.asList( "back-activity-ticket-inspec.html",
                                          "back-activity-ticket-inspec-list.html")); //驗票
//            htmlMap.put(7, Arrays.asList("")); //系統參數管理
            htmlMap.put(9, Arrays.asList("back-users-my-team.html")); //後台帳號及權限管理
//            htmlMap.put(10, Arrays.asList("")); //最新消息管理
            htmlMap.put(12, Arrays.asList("back-product-coupon_manage.html")); //商品優惠券管理
//            htmlMap.put(13, Arrays.asList("")); //帳務管理
            htmlMap.put(14, Arrays.asList("back-users-host.html")); //主辦單位管理
            htmlMap.put(15, Arrays.asList("back-product-order_manage.html")); //商品訂單管理
            htmlMap.put(16, Arrays.asList("back-product-comment_manager.html")); //商品評論管理
            htmlMap.put(17, Arrays.asList("back-product-product_manage.html",
                                          "back-product-update_product.html",
                                          "back-product-create_product.html",
                                          "back-product-pclass_manager.html")); //商品管理
            htmlMap.put(18, Arrays.asList("back-activity-torder_manage.html")); //票券訂單管理V
            htmlMap.put(19, Arrays.asList("back-activity-create-event.html",
                                          "back-activity-eventdashboard.html",
                                          "back-activity-sessionlist.html")); //節目管理
            htmlMap.put(20, Arrays.asList("back-activity-commentmgt.html")); //節目評論管理
            htmlMap.put(21, Arrays.asList("back-activity-seatmgt-allactivity.html",
                                          "back-activity-seatmgt-existed.html",
                                          "back-activity-seatmgt-notexisted.html",
                                          "back-activity-seatmgt-readonly.html",
                                          "back-activity-seatmgt-sellingandsoldsessions.html",
                                          "back-activity-seatmgt-sessioncanmodi.html",
                                          "back-activity-seatmgt-sessioncannotmodi.html",
                                          "back-activity-seatmgt-unsellsessions.html")); //場地座位管理
            htmlMap.put(22, Arrays.asList("back-activity-model-place.html",
                                          "back-activity-model-block.html",
                                          "back-activity-model-seats-exist.html",
                                          "back-activity-model-seats-notexist.html"));//場地模板座位管理
//            htmlMap.put(23, Arrays.asList("")); //節目區域及票價管理V

            //拿到點選進入的網址
            String requestURI = request.getRequestURI();
            int index = requestURI.lastIndexOf("/");
            String htmlName = requestURI.substring(index + 1);
            Integer funcno = null;
            for (Map.Entry<Integer, List<String>> entry : htmlMap.entrySet()) {
                List<String> htmlList = entry.getValue();
                for (String html : htmlList) {
                    if (html.equals(htmlName)) {
                        funcno = entry.getKey();
                        break;
                    }
                }
            }
            if (funcno == null || !baAllFuncno.contains(funcno)) {
                session.setAttribute("showAlert", "您沒有權限訪問此頁面。");
                response.sendRedirect(request.getContextPath() + "/back-index.html");
                return false;
            }
            return true;
        }
    }
}
