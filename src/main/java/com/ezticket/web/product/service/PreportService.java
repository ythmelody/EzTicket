package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Preport;
import com.ezticket.web.product.repository.PreportDAO;
import com.ezticket.web.product.repository.PreportDAOImpl;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

public class PreportService {
	private PreportDAO dao;

	public PreportService() {
		dao = new PreportDAOImpl();
	}

	public Preport addProductReport(Integer pcommentno, Integer memberno, String pwhy) {
		int preportstatus = 0;
		Timestamp preportdate =new Timestamp(System.currentTimeMillis());
		Preport preport = new Preport();
		preport.setPcommentno(pcommentno);
		preport.setMemberno(memberno);
		preport.setPwhy(pwhy);
		preport.setPreportstatus(preportstatus);
		preport.setPreportdate(preportdate); // �p�G�n�w�]�Ӧp��?
		dao.insert(preport);
		return preport;
	}
	
	public Preport updateProductReport(Preport preport) {
		final Preport oldPreportVO =dao.getByPrimaryKey(preport.getPreportno());
		System.out.println(preport.getPreportno());
		//�Q�n��������ק�
		preport.setPreportno(oldPreportVO.getPreportno());
		preport.setPcommentno(oldPreportVO.getPcommentno());
		preport.setMemberno(oldPreportVO.getMemberno());
		preport.setPreportdate(oldPreportVO.getPreportdate());
		dao.update(preport);
		return preport;
	}
	
	public Preport getOneProductReport(Integer preportno) {
		return dao.getByPrimaryKey(preportno);
	}
	
	public List<Preport> getAllProductReport(){
		return dao.getAll();
	}
	
	
	

}
