package com.ezticket.web.product.controller;

import com.ezticket.web.product.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;


@WebServlet("/updateProduct")
@MultipartConfig
public class UpdateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Integer productno = Integer.valueOf(request.getParameter("productno"));
		String pname = request.getParameter("pname");
		String ptag = request.getParameter("ptag");
		Integer pclassno = Integer.valueOf(request.getParameter("pclassno"));
		Integer hostno = Integer.valueOf(request.getParameter("hostno"));
		Integer pprice = Integer.valueOf(request.getParameter("pprice").trim());
		Integer pspecialprice = Integer.valueOf(request.getParameter("pspecialprice").trim());
		Integer pqty = Integer.valueOf(request.getParameter("pqty").trim());
		Timestamp psdate = Timestamp.valueOf(request.getParameter("psdate"));
		Timestamp pedate = Timestamp.valueOf(request.getParameter("pedate"));
		Integer pstatus = Integer.valueOf(request.getParameter("pstatus").trim());
		String pdiscrip = request.getParameter("pdiscrip");
		Integer pratetotal = Integer.valueOf(request.getParameter("pratetotal"));
		Integer prateqty = Integer.valueOf(request.getParameter("prateqty"));
		
		ProductService productService = new ProductService();
		productService.updateProduct(productno, pclassno, pname, hostno, pdiscrip, pprice, pspecialprice, pqty, psdate,
				pedate, ptag, pstatus, pratetotal, prateqty);
	}
}