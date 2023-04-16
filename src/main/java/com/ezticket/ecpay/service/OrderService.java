package com.ezticket.ecpay.service;

import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.Porder;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.repository.PdetailsRepository;
import com.ezticket.web.product.repository.PorderRepository;
import com.ezticket.web.product.repository.ProductDAO;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

	@Autowired
	private PorderRepository porderRepository;
	@Autowired
	private PdetailsRepository pdetailsRepository;
	@Autowired
	private ProductDAO productRepository;

	public String ecpayCheckout(Integer proderno) {
		
		String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
		
		AllInOne all = new AllInOne("");
		Porder porder = porderRepository.getReferenceById(proderno);
		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(uuId + proderno);
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String Now = now.format(formatter);
		obj.setMerchantTradeDate(Now);
		obj.setTotalAmount(porder.getPchecktotal().toString());
		obj.setTradeDesc("ezTicket 售票平台");
		String itemList = "";
		List<Pdetails> pdetailsList = pdetailsRepository.findByPorderno(proderno);
		for (int i = 0; i < pdetailsList.size(); i++) {
			Pdetails pdetails = pdetailsList.get(i);
			Product product = productRepository.getByPrimaryKey(pdetails.getPdetailsNo().getProductno());
			itemList += (i + 1) + ". " + product.getPname() + " x " + pdetails.getPorderqty() + " ";
		}
		obj.setItemName(itemList);
		InetAddress ip = null;
		try {
			// 使用可能會拋出異常的方法
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// 處理異常
			System.err.println(e);
		}
		
		String hostname = ip.getHostAddress();
		String returnURL = "http://" + hostname + ":8085/ecpayReturn";
		obj.setReturnURL(returnURL);
		obj.setNeedExtraPaidInfo("N");
		String form = all.aioCheckOut(obj, null);
		
		return form;
	}
}
