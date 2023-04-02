package com.ezticket.web.product.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.ezticket.web.product.pojo.Preport;
import com.ezticket.web.product.service.PcommentService;
import com.ezticket.web.product.service.PreportService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ProductCommentReportServlet")
public class ProductCommentReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PreportService PreportSvc;

	@Override
	public void init() throws ServletException {
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		PreportSvc = applicationContext.getBean(PreportService.class);

	}
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		if("productCommentRepostList".equals(action)){
			List<Preport> preportList = PreportSvc.getAllProductReport();
			System.out.println(preportList);
			Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
			String json =gson.toJson(preportList);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(json);
			pw.flush();
		}

	}

}
