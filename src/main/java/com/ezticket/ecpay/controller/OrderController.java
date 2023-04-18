package com.ezticket.ecpay.controller;

import com.ezticket.ecpay.service.OrderService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

@RestController
@RequestMapping("/ecpay")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	@Autowired
	PorderRepository porderRepository;

	@PostMapping("/checkout")
	public String ecpayCheckout(Integer porderno) {
		String aioCheckOutALLForm = orderService.ecpayCheckout(porderno);
		// 取得回傳的Form，然後導向綠界付款頁面
		// 用JS重導
		return aioCheckOutALLForm;
	}
	// 本機無法拿到資料要上Https
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
		// 印出所有K,V，參考看看，單純看有哪些回傳值..可以註解掉
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			System.out.println(paramName + ": " + request.getParameter(paramName));
		}
		// 綠界規定如有收到回傳，回饋給他的值
		return "1|OK";
	}

	// 訂單查詢 (/ecpay?merchantTradeNo=??????????)
	@GetMapping
	public String getEcpayStatus(String merchantTradeNo) {
		return orderService.checkorder(merchantTradeNo);
	}

}
