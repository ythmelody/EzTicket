package com.ezticket.web.product.controller;


import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.service.PimgtService;
import com.ezticket.web.product.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@WebServlet("/addProduct")
@MultipartConfig
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productSvc;
	private PimgtService pimgtSvc;

	@Override
	public void init() throws ServletException {
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		productSvc = applicationContext.getBean(ProductService.class);
		pimgtSvc =applicationContext.getBean(PimgtService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//上傳一筆新的資料
		String pname = request.getParameter("pname");
		String ptag = request.getParameter("ptag");
		Integer pclassno = Integer.valueOf(request.getParameter("pclassno"));
		Integer hostno = Integer.valueOf(request.getParameter("hostno"));
		Integer pprice = Integer.valueOf(request.getParameter("pprice").trim());
		Integer pspecialprice = Integer.valueOf(request.getParameter("pspecialprice").trim());
		Integer pqty = Integer.valueOf(request.getParameter("pqty").trim());
		java.sql.Timestamp psdate = java.sql.Timestamp.valueOf(request.getParameter("psdate"));
		java.sql.Timestamp pedate = java.sql.Timestamp.valueOf(request.getParameter("pedate"));
		Integer pstatus = Integer.valueOf(request.getParameter("pstatus").trim());
		String pdiscrip = request.getParameter("pdiscrip");
		Product product = productSvc.addProduct(pclassno, pname, hostno, pdiscrip, pprice, pspecialprice, pqty, psdate, pedate, ptag, pstatus);
		
		
		//多張圖片上傳
		Integer productno = product.getProductno();
		Collection<Part> parts = request.getParts();

		for (Part part : parts) {
			String filename = part.getSubmittedFileName();

			if (filename!= null  && filename.length()!=0){
				InputStream in = part.getInputStream();
				byte[] buf = new byte[in.available()];   // 也可以用byte[] buf = in.readAllBytes();  // Java 9 的新方法
				in.read(buf);
				in.close();
				pimgtSvc.addProductImg(productno,buf);
			}

		}

		
		
	
	
	}

}
