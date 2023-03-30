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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@WebServlet("/addProduct")
@MultipartConfig
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//上傳資料
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
		ProductService productService =new ProductService();
		Product product = productService.addProduct(pclassno, pname, hostno, pdiscrip, pprice, pspecialprice, pqty, psdate, pedate, ptag, pstatus);
		
		
		//多張圖片上傳
		PimgtService pimgtService =new PimgtService();
		Integer productno = product.getProductno();
		Collection<Part> parts = request.getParts();
//		System.out.println(parts);
		for (Part part : parts) {
			String filename = part.getSubmittedFileName();
//			if (filename != null) {
//				System.out.println("filename: " + filename + " length: " + filename.length());
//			}
			if (filename!= null  && filename.length()!=0){
				InputStream in = part.getInputStream();
				byte[] buf = new byte[in.available()];   // 也可以用byte[] buf = in.readAllBytes();  // Java 9 的新方法
				in.read(buf);
				in.close();
				pimgtService.addProductImg(productno,buf);
			}

		}

		
		
	
	
	}

}
