package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Pimgt;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.repository.Top10DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class Top10Service {

    @Autowired
    private Top10DAO top10DAO;

    @Autowired
    private  ProductService productSvc;

    @Autowired
    Pimgt pimgt;

    public List top10List(){
        List results = new ArrayList();
        Set<ZSetOperations.TypedTuple<String>> top10set = top10DAO.findAllValues();
        for(ZSetOperations.TypedTuple<String> item : top10set){
            Map result = new HashMap<>();
            String pno = item.getValue();
            Integer num = item.getScore().intValue();
            System.out.println("pno: " + pno + " num: " + num);
            Product product = productSvc.getOneProduct(Integer.valueOf(pno));
            result.put("productno", product.getProductno());
            result.put("pname", product.getPname());
            result.put("sale_num", num);
            List<Pimgt> pimgts= product.getPimgts();
//            for(int i = 0;;){
//                result.put("pimg", pimgts[i]);
//            }
            results.add(result);
        }
        return results;
    }

}



