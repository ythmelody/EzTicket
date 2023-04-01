package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.repository.PcommentDAO;
import com.ezticket.web.product.repository.Impl.PcommentDAOImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PcommentService {

	@Autowired
	private PcommentDAO dao;

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
	public Pcomment updateProductComment(Pcomment pcommentVO) {
		final Pcomment oldPcommentVO =dao.getByPrimaryKey(pcommentVO.getPcommentno());
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

	@Transactional
	public boolean deleteProductComment(Integer pcommentno) {
		return dao.delete(pcommentno);
	}

}
