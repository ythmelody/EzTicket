package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.repository.PcommentDAO;
import com.ezticket.web.product.repository.PcommentDAOImpl;

import java.sql.Timestamp;
import java.util.List;

public class PcommentService {

	private PcommentDAO dao;

	public PcommentService() {
		dao = new PcommentDAOImpl();
	}

	public Pcomment addProductComment(Integer productno, String pcommentcont, Integer prate, Integer memberno) {
		Timestamp pcommentdate = new Timestamp(System.currentTimeMillis());
		int pcommentstatus = 0;
		int plike = 0;
		
		Pcomment pcommentVO = new Pcomment();
		pcommentVO.setProductno(productno);
		pcommentVO.setPcommentcont(pcommentcont);
		pcommentVO.setPrate(prate);
		pcommentVO.setPcommentdate(pcommentdate);
		pcommentVO.setMemberno(memberno);
		pcommentVO.setPcommentstatus(pcommentstatus);
		pcommentVO.setPlike(plike);
		dao.insert(pcommentVO);
		return pcommentVO;
	}
	
	public Pcomment updateProductComment(Pcomment pcommentVO) {
		//���O���@�ӭn��s
		final Pcomment oldPcommentVO =dao.getByPrimaryKey(pcommentVO.getPcommentno());
		//�]�w��������ק�
		pcommentVO.setPcommentno(oldPcommentVO.getPcommentno());
		pcommentVO.setProductno(oldPcommentVO.getProductno());
		pcommentVO.setMemberno(oldPcommentVO.getMemberno());
		dao.update(pcommentVO);
		return pcommentVO;
	}
	
	public Pcomment getOneProductComment(Integer pcommentno) {
		return dao.getByPrimaryKey(pcommentno);	
	}
	
	public List<Pcomment> getAllProductComment(){
		return dao.getAll();
	}
	
	public List<Pcomment> getAllProductCommentOfOneMember(Integer memberno){
		return dao.getAllByMemberno(memberno);
	}
	
	public List<Pcomment> getAllProductCommentOfOneProduct(Integer productno){
		return dao.getAllByProductno(productno);
	}
	
	public boolean deleteProductComment(Integer pcommentno) {
		return dao.delete(pcommentno);
	}

}
