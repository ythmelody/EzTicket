package com.ezticket.core.config;

import com.ezticket.web.product.controller.ProductInfoServlet;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan("com.ezticket.web.*.controller")
public class ServletRegistrationConfig {

    @Bean
    public ServletRegistrationBean<ProductInfoServlet> productInfoServletRegistrationBean() {
        ServletRegistrationBean<ProductInfoServlet> registrationBean = new ServletRegistrationBean<>(new ProductInfoServlet());
        registrationBean.addUrlMappings("/ProductInfoServlet");
        return registrationBean;
    }

}