package com.ezticket.web.product.controller;

import com.ezticket.web.product.pojo.Pimgt;
import com.ezticket.web.product.service.PimgtService;
import com.ezticket.web.product.service.ProductService;
import com.fasterxml.jackson.databind.ser.AnyGetterWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/ProductImg")
public class ProductImg extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PimgtService pimgtSvc;

    public void init() throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        pimgtSvc = applicationContext.getBean(PimgtService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("deleteProdouctImg".equals(action)) {
            Integer deletePimgno = Integer.valueOf(request.getParameter("pimgno"));
            Boolean successful = pimgtSvc.deleteProductImg(deletePimgno);
            PrintWriter pw = response.getWriter();
            pw.print(successful);
            return;
        }


        Integer pimgno = Integer.valueOf(request.getParameter("pimgno"));
        Pimgt pimgt = pimgtSvc.getOneProductImg(pimgno);
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
