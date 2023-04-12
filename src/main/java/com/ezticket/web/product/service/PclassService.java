package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Pclass;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.repository.PclassDAO;
import com.ezticket.web.product.repository.Impl.PclassDaoImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PclassService {
    @Autowired
    private PclassDAO dao;

    @Transactional
    public Pclass addProductClass(String pclassname) {
        Pclass pclass = new Pclass();
        pclass.setPclassname(pclassname);
        dao.insert(pclass);
        return pclass;
    }

    @Transactional
    public Pclass updateProductClass(Integer pclassno, String pclassname) {
        Pclass pclass = dao.getByPrimaryKey(pclassno);
        pclass.setPclassno(pclassno);
        pclass.setPclassname(pclassname);
        dao.update(pclass);
        return pclass;
    }

    public Pclass getOneProductClass(Integer pclassno) {
        return dao.getByPrimaryKey(pclassno);
    }

    public List<Pclass> getAllProductClass() {
        return dao.getAll();
    }

    @Transactional
    public boolean deleteProductClass(Integer pclassno) {
        return dao.delete(pclassno);
    }

    public List<Pclass> getAllByPclassNameSearch(String pclassname) {
        return dao.getByPclasstName(pclassname);
    }

    public List<Pclass> comfiremIfRepeated(String pclassname) {
        return dao.getByCompletePclasstName(pclassname);
    }

}
