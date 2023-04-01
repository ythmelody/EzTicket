package com.ezticket.web.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ezticket.web.product.pojo.Pclass;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.service.PimgtService;
import com.ezticket.web.product.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


@WebServlet("/ProductInfoServlet")
public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productSvc;
	private PimgtService pimgtSvc;

	@Override
	public void init() throws ServletException {
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		productSvc = applicationContext.getBean(ProductService.class);
		pimgtSvc = applicationContext.getBean(PimgtService.class);
	}

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
			Product product = productSvc.getOneProduct(productno);
//			Pclass pclass = product.getPclass();
//			System.out.println("getPname"+product.getPname());
//			System.out.println("getPclassno"+pclass.getPclassno());
//			System.out.println("getPclassname"+pclass.getPclassname());
//			System.out.println("getPclass"+product.getPsdate());
//			System.out.println(product.getHostno());
//			System.out.println(product.getPimgts());
//			System.out.println(product.getPdiscrip());
//			System.out.println(product.getPprice());
//			System.out.println(product.getPspecialprice());
//			System.out.println(product.getPsdate());
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			String ujson = gson.toJson(product);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(ujson);
			pw.flush();
			return;

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// 商品列表呈現
		List<Product> allProductlist = productSvc.getAllProduct();
		for (Product p : allProductlist) {
//			System.out.println(p.getPname());
		}

		// 商品列表呈現(後臺商品管理需看見已下架商品)
		try {
			Integer allProduct = Integer.valueOf(request.getParameter("allProduct"));

			if (allProduct == 1) {
				Gson gson = new Gson();
				String ujson = gson.toJson(allProductlist);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
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

		//商品複合查詢
		String action =request.getParameter("action");
		if("ProductSearchForm".equals(action)){
			Map<String,String[]> map=request.getParameterMap(); //將得到的資料轉成map
			System.out.println("我有跑進來ProductSearchForm"+map);
			List<Product> productList =productSvc.getAllByproductSearch(map); //轉交進行複合查詢
			String productListJson = gson.toJson(productList);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			pw.print(productListJson);
			pw.flush();
		}
	}


}
