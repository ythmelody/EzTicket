package com.ezticket.web.product.repository.Impl;

import java.util.List;

import com.ezticket.web.product.pojo.Preport;
import com.ezticket.web.product.repository.PreportDAO;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class PreportDAOImpl implements PreportDAO {
	@PersistenceContext
	private Session session;
	@Override
	public void insert(Preport preport) {
		session.persist(preport);
	}

	@Override
	public void update(Preport preport) {
		final StringBuilder hql =new StringBuilder().append("UPDATE Preport SET ");
		hql.append("pwhy = :pwhy,")
		   .append("preportstatus = :preportstatus ")
		   .append("where preportno = :preportno");

			Query<?> query= session.createQuery(hql.toString());
			query.setParameter("pwhy",preport.getPwhy());
			query.setParameter("preportstatus", preport.getPreportstatus());
			query.setParameter("preportno", preport.getPreportno());
			query.executeUpdate();
		
	}

	@Override
	public List<Preport> getAll() {
		final String hql ="FROM Preport ORDER BY preportno";
		return session.createQuery(hql,Preport.class).getResultList();
	}

	@Override
	public Preport getByPrimaryKey(Integer preportno) {
		return session.get(Preport.class,preportno);
	}

}
