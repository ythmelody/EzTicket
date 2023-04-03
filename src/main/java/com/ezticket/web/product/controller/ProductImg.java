package com.ezticket.web.product.controller;

import com.ezticket.web.product.pojo.Pimgt;
import com.ezticket.web.product.service.PimgtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;




@WebServlet("/ProductImg")
public class ProductImg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PimgtService pimgSvc = new PimgtService();
//		Integer productno = Integer.valueOf(request.getParameter("productno"));
//		List<Pimgt> productImgList = pimgSvc.getAllImgByProductNo(productno);
//		response.setContentType("image/gif");
//		byte[] array = productImgList.get(0).getPimg();
//		ServletOutputStream out = response.getOutputStream();
//		ByteArrayInputStream input = new ByteArrayInputStream(array);
//
//		byte[] buf = new byte[4 * 1024]; // 4K buffer
//		int len;
//		while ((len = input.read(buf)) != -1) {
//			out.write(buf, 0, len);
//		}
//		input.close();

		Integer pimgno = Integer.valueOf(request.getParameter("pimgno"));
		Pimgt pimgt = pimgSvc.getOneProductImg(pimgno);
		response.setContentType("image/gif");
		byte[] array = pimgt.getPimg();
		ServletOutputStream out = response.getOutputStream();
		ByteArrayInputStream input = new ByteArrayInputStream(array);

		byte[] buf = new byte[4 * 1024]; // 4K buffer
		int len;
		while ((len = input.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		input.close();

	}
}
