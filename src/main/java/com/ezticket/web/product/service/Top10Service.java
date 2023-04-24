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
    private ProductService productSvc;


    public List top10List() {
        List results = new ArrayList();
        Set<ZSetOperations.TypedTuple<String>> top10set = top10DAO.findAllValues();
        for (ZSetOperations.TypedTuple<String> item : top10set) {
            Map result = new HashMap<>();
            String pno = item.getValue();
            Integer num = item.getScore().intValue();
            Product product = productSvc.getOneProduct(Integer.valueOf(pno));
            result.put("productno", product.getProductno());
            result.put("pname", product.getPname());
            result.put("sale_num", num);
            result.put("pprice", product.getPprice());
            result.put("pspecialprice", product.getPspecialprice());
            List<Pimgt> pimgts = product.getPimgts();
            result.put("pimgno", pimgts.get(0).getPimgno());

            results.add(result);
        }
        return results;
    }

    public List<Object[]>  indexSearch(String keyword){
        List results = new ArrayList();
        List<Object[]> objs = top10DAO.search(keyword, 1, 10);
        for (Object[] obj : objs) {
//            System.out.println(obj[0]);
            Map result = new HashMap();
            result.put("name", obj[0]);
            result.put("no", obj[1]);
            result.put("type", obj[2]);
            results.add(result);
        }
        return results;
    }

}



