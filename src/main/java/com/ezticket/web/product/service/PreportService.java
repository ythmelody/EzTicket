package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Preport;
import com.ezticket.web.product.repository.PreportDAO;
import com.ezticket.web.product.repository.Impl.PreportDAOImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PreportService {

    @Autowired
    private PreportDAO dao;

    @Transactional
    public Preport addProductReport(Integer pcommentno, Integer memberno, String pwhy) {
        int preportstatus = 0;
        Timestamp preportdate = new Timestamp(System.currentTimeMillis());
        Preport preport = new Preport();
        preport.setPcommentno(pcommentno);
        preport.setMemberno(memberno);
        preport.setPwhy(pwhy);
        preport.setPreportstatus(preportstatus);
        preport.setPreportdate(preportdate); // �p�G�n�w�]�Ӧp��?
        dao.insert(preport);
        return preport;
    }

    @Transactional
    public Preport updateProductReport(Preport preport) {
        final Preport oldPreportVO = dao.getByPrimaryKey(preport.getPreportno());
        preport.setPreportno(oldPreportVO.getPreportno());
        preport.setPcommentno(oldPreportVO.getPcommentno());
        preport.setMemberno(oldPreportVO.getMemberno());
        preport.setPreportdate(oldPreportVO.getPreportdate());
        dao.update(preport);
        return preport;
    }
    @Transactional
    public boolean updateProductReport(Integer preportno, Integer preportstatus) {
       Preport preport = dao.getByPrimaryKey(preportno);
        preport.setPreportstatus(preportstatus);
        dao.update(preport);
        return true;
    }
    public Preport getOneProductReport(Integer preportno) {
        return dao.getByPrimaryKey(preportno);
    }

    public List<Preport> getAllProductReport() {
        return dao.getAll();
    }





}
