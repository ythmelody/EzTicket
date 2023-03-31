package com.ezticket.web.product.repository.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.repository.PcommentDAO;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PcommentDAOImpl implements PcommentDAO {
	@PersistenceContext
	private Session session;
	
	private static final String GET_ALL_BY_MemberNO = 
			"SELECT * FROM pcomment WHERE memberno = ? order by pcommentno";

	@Override
	public void insert(Pcomment pcommentVO) {
		session.persist(pcommentVO);
	}

	@Override
	public void update(Pcomment pcommentVO) {
		final StringBuilder hql =new StringBuilder().append("UPDATE PcommentVO SET ");
		hql.append("pcommentcont = :pcommentcont,")
		.append("prate = :prate,")
		.append("pcommentdate = :pcommentdate,")
		.append("pcommentstatus = :pcommentstatus,")
		.append("plike = :plike ")
		.append("WHERE pcommentno = :pcommentno");
		
		Query<?>query=session.createQuery(hql.toString());
		query.setParameter("pcommentcont", pcommentVO.getPcommentcont());
		query.setParameter("prate", pcommentVO.getPrate());
		query.setParameter("pcommentdate", pcommentVO.getPcommentdate());
		query.setParameter("pcommentstatus", pcommentVO.getPcommentcont());
		query.setParameter("plike", pcommentVO.getPlike());
		query.setParameter("pcommentno", pcommentVO.getPcommentno());
		query.executeUpdate();
	}

	@Override
	public Pcomment getByPrimaryKey(Integer pcommentno) {
		return session.get(Pcomment.class, pcommentno);
	}

	@Override
	public List<Pcomment> getAllByMemberno(Integer memberno) {
		final String hql = "FROM PcommentVO WHERE memberno = :memberno ORDER BY pcommentno";
		return session
				.createQuery(hql, Pcomment.class)
				.setParameter("memberno", memberno)
				.getResultList();

	}





	@Override
	public List<Pcomment> getAll() {
		final String hql ="FROM Pcomment ORDER BY pcommentno";
		return session.createQuery(hql,Pcomment.class).getResultList();
	}

	@Override
	public boolean delete(Integer pcommentno) {
		Pcomment pcommentVO =session.get(Pcomment.class,pcommentno);
		session.remove(pcommentVO);
		return true;
	}

	@Override
	public List<Pcomment> getAllByProductno(Integer productno) {
		final String hql ="FROM Pcomment WHERE productno =:productno ORDER BY plike";
		return session
				.createQuery(hql,Pcomment.class)
				.setParameter("productno",productno)
				.getResultList();
	}


}
