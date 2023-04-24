package com.ezticket.ecpay.controller;

import com.ezticket.core.service.EmailService;
import com.ezticket.ecpay.service.OrderService;
import com.ezticket.ecpay.service.TcatService;
import com.ezticket.web.activity.pojo.Torder;
import com.ezticket.web.activity.service.CollectCrudService;
import com.ezticket.web.activity.service.TorderService;
import com.ezticket.web.product.pojo.Porder;
import com.ezticket.web.product.repository.PorderRepository;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
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
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	CollectCrudService collectCrudService;

    @Autowired
    TorderService torderService;
	@Autowired
	TcatService tcatService;

	@PostMapping("/tcat")
	public String createHomeOrder(Integer porderno) throws UnsupportedEncodingException {
		String createTcatOrder = tcatService.postCreateHomeOrder(porderno);
		return createTcatOrder;
	}
	@PostMapping("/tcat/checkout")
	public String printOrder (Integer porderno) {
		Porder porder = porderRepository.getReferenceById(porderno);
		if (porder.getLogisticsid() == null){
			return "單據不存在或尚未建立單據";
		}
		String tcatOrder = tcatService.postPrintTradeDocumentOrder(porder.getLogisticsid());
		return tcatOrder;
	}
	@PostMapping("/tcat/return")
	public String tcatReturn(HttpServletRequest request) {
		Enumeration<String> parameterNames = request.getParameterNames();
		String rtnCode = request.getParameter("RtnCode");
		String tcatDate = request.getParameter("UpdateStatusDate");
		String porderno = request.getParameter("MerchantTradeNo").substring(15);
		String allPayId = request.getParameter("AllPayLogisticsID");
		Porder porder = porderRepository.getReferenceById(Integer.valueOf(porderno));
		// 塞入出貨日期 // 更改訂單狀態
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		if(rtnCode.equals("300")){
			porder.setPshipdate(LocalDateTime.parse(tcatDate, formatter));
			porder.setPprocessstatus(1);
			porder.setLogisticsid(allPayId);
			porderRepository.save(porder);
		}
		// 寄出貨信
		Member member = memberRepository.getReferenceById(porder.getMemberno());
		emailService.sendOrderMail(member.getMname(),member.getMemail(),porder.getPorderno().toString(), String.valueOf(4));
		// 印出所有K,V，參考看看，單純看有哪些回傳值..可以註解掉
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			System.out.println(paramName + ": " + request.getParameter(paramName));
		}
		// 綠界規定如有收到回傳，回饋給他的值
		return "1|OK";
	}
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
		Member member = memberRepository.getReferenceById(porder.getMemberno());
		emailService.sendOrderMail(member.getMname(),member.getMemail(),porder.getPorderno().toString(), String.valueOf(2));
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
		collectCrudService.insertCollect(torder);

        // 印出所有K,V，參考看看
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }

        return "1|OK";
    }
}
