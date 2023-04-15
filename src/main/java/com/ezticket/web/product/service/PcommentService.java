package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.repository.PcommentDAO;
import com.ezticket.web.product.repository.Impl.PcommentDAOImpl;
import com.ezticket.web.product.repository.PcommentRedisDAO;
import com.ezticket.web.product.repository.ProductDAO;
import com.ezticket.web.product.util.PageResult;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PcommentService {

    @Autowired
    private PcommentDAO dao;

    @Autowired
    private PcommentRedisDAO pcommentRedisDAO;
    @Autowired
    private ProductDAO productDAO;

    @Transactional
    public Pcomment addProductComment(Integer productno, String pcommentcont, Integer prate, Integer memberno) {
        Timestamp pcommentdate = new Timestamp(System.currentTimeMillis());
        int pcommentstatus = 0;
        int plike = 0;

        Pcomment pcomment = new Pcomment();
        pcomment.setProductno(productno);
        pcomment.setPcommentcont(pcommentcont);
        pcomment.setPrate(prate);
        pcomment.setPcommentdate(pcommentdate);
        pcomment.setMemberno(memberno);
        pcomment.setPcommentstatus(pcommentstatus);
        pcomment.setPlike(plike);
        dao.insert(pcomment);
        return pcomment;
    }

    @Transactional
    public Pcomment updateProductComment(Pcomment pcomment) {
        final Pcomment oldPcomment = dao.getByPrimaryKey(pcomment.getPcommentno());
        pcomment.setPcommentno(oldPcomment.getPcommentno());
        pcomment.setProductno(oldPcomment.getProductno());
        pcomment.setMemberno(oldPcomment.getMemberno());
        dao.update(pcomment);
        return pcomment;
    }

    @Transactional
    public boolean updateProductComment(Integer pcommentno, Integer pcommentstatus) {
        return dao.update(pcommentno, pcommentstatus);
    }

    @Transactional
    public boolean updateProductComment(Integer pcommentno, Integer prate, String pcommentcont) {
        //更新商品評論
        Pcomment pcomment = dao.getByPrimaryKey(pcommentno);

        dao.update(pcomment);
        //同時更新商品總評星
        Product product =productDAO.getByPrimaryKey(pcomment.getProductno());
        System.out.print("原始的product.getPratetotal()="+product.getPratetotal());
        System.out.print("原始的pcomment.getPrate()="+pcomment.getPrate());
        product.setPratetotal(product.getPratetotal() - pcomment.getPrate() + prate);
        System.out.print("更新過的product.getPratetotal()"+product.getPratetotal());

        pcomment.setPrate(prate);
        pcomment.setPcommentcont(pcommentcont);
        productDAO.update(product);
        return dao.update(pcomment);
    }


    public Pcomment getOneProductComment(Integer pcommentno) {
        return dao.getByPrimaryKey(pcommentno);
    }

    public List<Pcomment> getAllProductComment() {
        return dao.getAll();
    }

    public List<Pcomment> getAllProductCommentOfOneMember(Integer memberno) {
        return dao.getAllByMemberno(memberno);
    }

    public List<Pcomment> getAllProductCommentOfOneProduct(Integer productno) {
        return dao.getAllByProductno(productno);
    }

    @Transactional
    public boolean deleteProductComment(Integer pcommentno) {
        return dao.delete(pcommentno);
    }

    public List<Pcomment> getAllBySearch(Map<String, String[]> map) {
        return dao.getAll(map);
    }


    //更新評論按讚
    @Transactional
    public Pcomment updatePcomment(Integer pcommentno, Integer thumpup) {
        Pcomment pcomment = dao.getByPrimaryKey(pcommentno);
        Integer plike = pcomment.getPlike();
        pcomment.setPlike(plike + thumpup);
        dao.update(pcomment);
        return pcomment;
    }

    //節目評論按讚
    @Transactional
    public boolean addThumpUp(Integer memberno, Integer pcommentno) {
        try {
            pcommentRedisDAO.addKeyValue("thumbup:product:" + memberno, String.valueOf(pcommentno));
            updatePcomment(pcommentno, 1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //商品評論取消按讚
    @Transactional
    public boolean removeThumpUp(Integer memberno, Integer pcommentno) {

        try {
            pcommentRedisDAO.deleteKeyValue("thumbup:product:" + memberno, String.valueOf(pcommentno));
            updatePcomment(pcommentno, -1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //取得會員按讚的商品編號
    public Set<Integer> getPcommentnosByMemberno(Integer memberno) {
        Set<String> pcommentnos = pcommentRedisDAO.findAllValues("thumbup:product:" + memberno);
        Set<Integer> set = new HashSet<>();
        for (String pcommentno : pcommentnos) {
            set.add(Integer.valueOf(pcommentno));
        }
        return set;
    }

    //查詢搭配分頁
    public PageResult<Pcomment> getAllBySearch(Map<String, String[]> map, Integer pageNumber, Integer pageSize) {
        return dao.getAll(map, pageNumber, pageSize);
    }


}
