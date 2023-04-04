package com.ezticket.web.product.repository.Impl;

import java.util.List;

import com.ezticket.web.product.pojo.Pclass;
import com.ezticket.web.product.repository.PclassDAO;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PclassDaoImpl implements PclassDAO {
	@PersistenceContext
	private Session session;
	@Override
	public void insert(Pclass pclassVO) {
		session.persist(pclassVO);
	}

	@Override
	public void update(Pclass pclassVO) {
	final String hql ="UPDATE PclassVO SET pclassname = :pclassname WHERE pclassno = :pclassno";
	
	Query<?> query =session.createQuery(hql);
	query.setParameter("pclassname",pclassVO.getPclassname());
	query.setParameter("pclassno",pclassVO.getPclassno());
	query.executeUpdate();
	}

	@Override
	public Pclass getByPrimaryKey(Integer pclassno) {
		return session.get(Pclass.class, pclassno);
	}

	@Override
	public List<Pclass> getAll() {
		final String hql = "FROM Pclass ORDER BY pclassno";
		return session.createQuery(hql, Pclass.class).getResultList();
	}

	@Override
	public boolean delete(Integer pclassno) {
		Pclass pclassVO = session.get(Pclass.class, pclassno);
		session.remove(pclassVO);
		return true;

	}

}
