package com.ezticket.web.product.repository;

import java.util.List;

import com.ezticket.web.product.pojo.Preport;
import org.hibernate.Transaction;
import org.hibernate.query.Query;



public class PreportDAOImpl implements PreportDAO {

	@Override
	public void insert(Preport preport) {
//		SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.openSession();
		
		try {
			Transaction transaction = getSession().beginTransaction();
			getSession().persist(preport);
			transaction.commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void update(Preport preport) {
		final StringBuilder hql =new StringBuilder().append("UPDATE Preport SET ");
		hql.append("pwhy = :pwhy,")
		   .append("preportstatus = :preportstatus ")
		   .append("where preportno = :preportno");

		
		try {
			Transaction transaction =getSession().beginTransaction();
			Query<?> query= getSession().createQuery(hql.toString());
			query.setParameter("pwhy",preport.getPwhy());
			query.setParameter("preportstatus", preport.getPreportstatus());
			query.setParameter("preportno", preport.getPreportno());
			query.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Preport> getAll() {
		final String hql ="FROM Preport ORDER BY preportno";
//		SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
//		Session session =sessionFactory.openSession();
		return getSession().createQuery(hql,Preport.class).getResultList();
	}

	@Override
	public Preport getByPrimaryKey(Integer preportno) {
//		SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
//		Session session =sessionFactory.openSession();
		return getSession().get(Preport.class,preportno);
	}

}
