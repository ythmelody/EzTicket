package com.ezticket.web.product.controller;


import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.service.PcommentService;
import com.ezticket.web.product.service.PimgtService;
import com.ezticket.web.product.service.ProductService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ProductCommentServlet")
public class ProductCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PcommentService pcommentSvc;
	@Override
	public void init() throws ServletException {
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		pcommentSvc = applicationContext.getBean(PcommentService.class);
	}
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Integer productno =Integer.valueOf(request.getParameter("productno"));
	List<Pcomment> pcommentList =pcommentSvc.getAllProductCommentOfOneProduct(productno);
	//轉換成json格式寫出
	Gson gson =new Gson();
	String json =gson.toJson(pcommentList);
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	PrintWriter pw = response.getWriter();
	pw.print(json);
	pw.flush();
	}

}
