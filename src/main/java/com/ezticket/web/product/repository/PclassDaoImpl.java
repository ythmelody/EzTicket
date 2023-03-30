package com.ezticket.web.product.repository;

import java.util.List;

import com.ezticket.web.product.pojo.Pclass;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PclassDaoImpl implements PclassDAO {

	@Override
	public void insert(Pclass pclassVO) {
		getSession().persist(pclassVO);
	}

	@Override
	public void update(Pclass pclassVO) {
	final String hql ="UPDATE PclassVO SET pclassname = :pclassname WHERE pclassno = :pclassno";
	
	Query<?> query =getSession().createQuery(hql);
	query.setParameter("pclassname",pclassVO.getPclassname());
	query.setParameter("pclassno",pclassVO.getPclassno());
	query.executeUpdate();
	}

	@Override
	public Pclass getByPrimaryKey(Integer pclassno) {
		return getSession().get(Pclass.class, pclassno);
	}

	@Override
	public List<Pclass> getAll() {
		final String hql = "FROM PclassVO ORDER BY pclassno";
		return getSession().createQuery(hql, Pclass.class).getResultList();
	}

	@Override
	public boolean delete(Integer pclassno) {
		Session session = getSession();
		Pclass pclassVO = session.get(Pclass.class, pclassno);
		session.remove(pclassVO);
		return true;

	}

}
