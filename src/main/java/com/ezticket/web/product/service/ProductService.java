package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.repository.ProductDAO;
import com.ezticket.web.product.util.PageResult;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service

public class ProductService {
    @Autowired
    private ProductDAO dao;

    @Transactional
    public Product addProduct(Integer pclassno, String pname, Integer hostno, String pdiscrip, Integer pprice,
                              Integer pspecialprice, Integer pqty, Timestamp psdate, Timestamp pedate, String ptag, Integer pstatus) {
        Product productVO = new Product();
        productVO.setPratetotal(0);
        productVO.setPrateqty(0);

        productVO.setPclassno(pclassno);
        productVO.setPname(pname);
        productVO.setHostno(hostno);
        productVO.setPdiscrip(pdiscrip);
        productVO.setPprice(pprice);
        productVO.setPspecialprice(pspecialprice);
        productVO.setPqty(pqty);
        productVO.setPsdate(psdate);
        productVO.setPedate(pedate);
        productVO.setPtag(ptag);
        productVO.setPstatus(pstatus);

        dao.insert(productVO);
        return productVO;
    }

    @Transactional
    public Product updateProduct(Integer productno, Integer pclassno, String pname, Integer hostno, String pdiscrip,
                                 Integer pprice, Integer pspecialprice, Integer pqty, Timestamp psdate, Timestamp pedate, String ptag,
                                 Integer pstatus, Integer pratetotal, Integer prateqty) {

        Product productVO = new Product();
        productVO.setProductno(productno);
        productVO.setPclassno(pclassno);
        productVO.setPname(pname);
        productVO.setHostno(hostno);
        productVO.setPdiscrip(pdiscrip);
        productVO.setPprice(pprice);
        productVO.setPspecialprice(pspecialprice);
        productVO.setPqty(pqty);
        productVO.setPsdate(psdate);
        productVO.setPedate(pedate);
        productVO.setPtag(ptag);
        productVO.setPstatus(pstatus);
        productVO.setPratetotal(pratetotal);
        productVO.setPrateqty(prateqty);
        dao.update(productVO);

        return productVO;
    }

    public Product updateProduct(Integer productno, Integer prate) {

        Product product = dao.getByPrimaryKey(productno);
        Integer pratetotal = product.getPratetotal();
        Integer prateqty = product.getPrateqty();
        product.setPratetotal(pratetotal + prate);
        product.setPrateqty(prateqty + 1);
        dao.update(product);
        return product;
    }


    public Product getOneProduct(Integer productno) {
        return dao.getByPrimaryKey(productno);
    }

    public List<Product> getAllProduct() {
        return dao.getAll();
    }

    public List<Product> getAllByName(String pname) {
        return dao.findByProductName(pname);
    }

    public List<Product> getAllByproductSearch(Map<String, String[]> map) {
        return dao.getAll(map);
    }

    //複合查詢搭配分頁
    public PageResult<Product> getAllByproductSearch(Map<String, String[]> map, Integer pageNumber, Integer pageSize) {
        return dao.getAll(map, pageNumber, pageSize);
    }

    //將過了下架時間的商品狀態更動為下架
    // 這是一個 Spring 框架的定時任務設定，表示每小時的整點觸發一次，其中各個欄位的意義如下：
    // 第一個 * 代表秒數，表示不限定秒數。
    // 第二個 0 代表分鐘數，表示每小時的 0 分鐘觸發。
    // 第三個 * 代表小時數，表示每小時都要觸發。
    // 第四個 * 代表天數，表示不限定天數。
    // 第五個 * 代表月份，表示不限定月份。
    // 第六個 * 代表星期幾，表示不限定星期幾。
    @Scheduled(cron = "0 0 * * * *")
    public void checkPstatus() {
        Date date = new java.util.Date();
        Timestamp today = new Timestamp(date.getTime());
        List<Product> productList = dao.findExpiredProduct(today);

        for (Product product : productList) {
            product.setPstatus(1);
            dao.update(product);
        }
    }
}
