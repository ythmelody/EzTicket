package com.ezticket.ecpay.controller;

import com.ezticket.ecpay.service.OrderService;
import com.ezticket.web.activity.dto.OrderTicketDTO;
import com.ezticket.web.activity.pojo.Torder;
import com.ezticket.web.activity.service.TorderService;
import com.ezticket.web.product.pojo.Porder;
import com.ezticket.web.product.repository.PorderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/ecpay")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    PorderRepository porderRepository;

    @Autowired
    TorderService torderService;

    @PostMapping("/checkout")
    public String ecpayCheckout(Integer porderno) {
        String aioCheckOutALLForm = orderService.ecpayCheckout(porderno);
        // 取得回傳的Form，然後導向綠界付款頁面
        return "redirect:" + aioCheckOutALLForm;
    }

    // 本機無法拿到資料要上http
    @PostMapping("/return")
    public String ecpayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Enumeration<String> parameterNames = request.getParameterNames();
        String rtnCode = request.getParameter("RtnCode");
        String paymentDate = request.getParameter("PaymentDate");
        String porderno = request.getParameter("MerchantTradeNo").substring(15);
        Porder porder = porderRepository.getReferenceById(Integer.valueOf(porderno));
        // 塞入付款日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        porder.setPpaydate(LocalDateTime.parse(paymentDate, formatter));
        // 更改付款狀態
        porder.setPpaymentstatus(Integer.valueOf(rtnCode));
        porderRepository.save(porder);
        // 印出所有K,V，參考看看
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }
        String url = "/front-product-order_confirmed.html?id=" + porderno;
        response.sendRedirect(url);
        return "1|OK";
    }

    // 訂單查詢 (/ecpay?merchantTradeNo=??????????)
    @GetMapping
    public String getEcpayStatus(String merchantTradeNo) {

        return orderService.checkorder(merchantTradeNo);
    }

    //	========================================== 票券訂單 ================================================
    @PostMapping("/Treturn")
    public String ecpayTReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Enumeration<String> parameterNames = request.getParameterNames();
        String rtnCode = request.getParameter("RtnCode");
        String paymentDate = request.getParameter("PaymentDate");
        String torderno = request.getParameter("MerchantTradeNo").substring(15);

        Torder torder = torderService.getById(Integer.valueOf(torderno));
        // 塞入付款日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        torder.setTpayDate(Timestamp.valueOf(LocalDateTime.parse(paymentDate, formatter)));
        // 更改付款狀態
        torder.setTpaymentStatus(Integer.valueOf(rtnCode));
        // 更改處理狀態
        torder.setTprocessStatus(Integer.valueOf(rtnCode));
        torderService.updateTorder(torder);

        // 票券 QR code 於此產生 - 1 (Melody)

        // 印出所有K,V，參考看看
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }

        return "1|OK";
    }
}
