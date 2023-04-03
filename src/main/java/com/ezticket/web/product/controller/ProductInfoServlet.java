package com.ezticket.web.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/ProductInfoServlet")
public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 單一商品詳情呈現
			// 取得商品編號 => 取得單一商品資訊
			Integer productno = Integer.valueOf(request.getParameter("productno"));
			ProductService productSvc = new ProductService();
			Product product = productSvc.getOneProduct(productno);
//			System.out.println(product.getPsdate());
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			String ujson = gson.toJson(product);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-store"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response.setDateHeader("Expires", 0);
			PrintWriter pw = response.getWriter();
			pw.print(ujson);
			pw.flush();
			return;

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// 商品列表呈現
		ProductService productSvc = new ProductService();
		List<Product> allProductlist = productSvc.getAllProduct();
		for (Product p : allProductlist) {
			System.out.println(p.getPname());
		}

		// 商品列表呈現(後臺商品管理需看見已下架商品)
		try {
			Integer allProduct = Integer.valueOf(request.getParameter("allProduct"));

			if (allProduct == 1) {
				Gson gson = new Gson();
				String ujson = gson.toJson(allProductlist);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-store"); // HTTP 1.1
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0
				response.setDateHeader("Expires", 0);
				PrintWriter pw = response.getWriter();
				pw.print(ujson);  
				pw.flush();
				return;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 商品列表呈現(過濾掉未上架商品)
		List<Product> launchProduct = new ArrayList<>();
		for (Product product : allProductlist) {
			if (product.getPstatus() == 1) {
				launchProduct.add(product);
			}
		}
		Gson gson = new Gson();
		String ujson = gson.toJson(launchProduct);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(ujson);
		pw.flush();
	}

}
