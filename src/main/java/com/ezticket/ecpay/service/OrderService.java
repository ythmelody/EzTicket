package com.ezticket.ecpay.service;

import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.Porder;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.repository.PdetailsRepository;
import com.ezticket.web.product.repository.PorderRepository;
import com.ezticket.web.product.repository.ProductDAO;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import ecpay.payment.integration.domain.QueryTradeInfoObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

	@Autowired
	private PorderRepository porderRepository;
	@Autowired
	private PdetailsRepository pdetailsRepository;
	@Autowired
	private ProductDAO productRepository;

	public String ecpayCheckout(Integer proderno) {

		// 綠界的方法裡面都有註解，可以點進去看

		// 建立AllInOne物件
		AllInOne all = new AllInOne("");
		// 取得訂單
		Porder porder = porderRepository.getReferenceById(proderno);
		AioCheckOutALL obj = new AioCheckOutALL();
		// // 綠界規定須20碼,放入訂單編號15碼+訂單編號5碼
		obj.setMerchantTradeNo("ezTicket0000000" + proderno);
		// 取得當前時間，放入時間
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String Now = now.format(formatter);
		obj.setMerchantTradeDate(Now);
		// 放入結帳金額
		obj.setTotalAmount(porder.getPchecktotal().toString());
		// 交易敘述
		obj.setTradeDesc("ezTicket 售票平台");
		// 商品明細只能是一個字串 <br> \n 能換行的都試過了，不要浪費時間
		// 將品項一個一個加入字串中
		String itemList = "";
		List<Pdetails> pdetailsList = pdetailsRepository.findByPorderno(proderno);
		for (int i = 0; i < pdetailsList.size(); i++) {
			Pdetails pdetails = pdetailsList.get(i);
			Product product = productRepository.getByPrimaryKey(pdetails.getPdetailsNo().getProductno());
			itemList += (i + 1) + ". " + product.getPname() + " x " + pdetails.getPorderqty() + ", ";
		}
		// 塞入商品明細
		obj.setItemName(itemList);


		// 這段沒用了，沒有HTTP收不到綠界回傳值-----------------------------------------------
//		// 因應大家IP不同，用方法取得自己的ip
//		InetAddress ip = null;
//		try {
//			// 使用可能會拋出異常的方法
//			ip = InetAddress.getLocalHost();
//		} catch (UnknownHostException e) {
//			// 處理異常
//			System.err.println(e);
//		}
//		String hostname = ip.getHostAddress();
//		// 交易成功時回傳值接收的路徑，但是收不到還在測試中
//		String returnURL = "https://" + hostname + ":8085/ecpay/return";
		// -------------------------------------------------------------------------------

		//	放入你自己架的HTTP
		//	沒有的話下載並安裝 Ngrok，並註冊一個 Ngrok 帳號。
		//	啟動本地伺服器，例如 Tomcat 或是 Spring Boot。
		//	在命令列輸入 ngrok http 8080，其中 8080 是你本機伺服器的 Port，請依實際情況更改。
		//	Ngrok 會顯示一個公開的 URL，例如 http://xxxxxx.ngrok.io，複製此 URL。
		String returnURL = "https://7699-111-249-7-40.jp.ngrok.io";

		obj.setReturnURL(returnURL + "/ecpay/return");
		// 是否需要額外的付款資訊ngrok http 8080
		obj.setNeedExtraPaidInfo("N");
		// 會回傳一個form表單
		String form = all.aioCheckOut(obj, null);

		return form;
	}


	// 訂單查詢
	public String checkorder(String merchantTradeNo){
		QueryTradeInfoObj queryTradeInfoObj = new QueryTradeInfoObj();
		queryTradeInfoObj.setMerchantID("2000132");
		queryTradeInfoObj.setMerchantTradeNo(merchantTradeNo);
		queryTradeInfoObj.setTimeStamp(String.valueOf(Instant.now().getEpochSecond()));
		AllInOne allInOne = new AllInOne("");
		return allInOne.queryTradeInfo(queryTradeInfoObj);
	}
	public void orderPayStatus(){

	}


}
