package com.ezticket.ecpay.controller;

import com.ezticket.ecpay.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;

	@PostMapping("/ecpayCheckout")
	public String ecpayCheckout(Integer porderno) {
		String aioCheckOutALLForm = orderService.ecpayCheckout(porderno);
		return "redirect:" + aioCheckOutALLForm;
	}

	@PostMapping("/ecpayReturn")
	public String ecpayReturn(HttpServletRequest request) {
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			System.out.println(paramName + ": " + request.getParameter(paramName));
		}
		return "1|OK";
	}

}
