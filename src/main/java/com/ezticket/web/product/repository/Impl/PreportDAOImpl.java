package com.ezticket.web.product.repository.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ezticket.web.product.pojo.Preport;
import com.ezticket.web.product.repository.PreportDAO;
import com.ezticket.web.product.util.PageResult;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
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
    public Boolean update(Preport preport) {
        final StringBuilder hql = new StringBuilder().append("UPDATE Preport SET ");
        hql.append("pwhy = :pwhy,")
                .append("preportstatus = :preportstatus ")
                .append("where preportno = :preportno");

        Query<?> query = session.createQuery(hql.toString());
        query.setParameter("pwhy", preport.getPwhy());
        query.setParameter("preportstatus", preport.getPreportstatus());
        query.setParameter("preportno", preport.getPreportno());
        query.executeUpdate();

        return true;
    }

    @Override
    public List<Preport> getAll() {
        final String hql = "FROM Preport ORDER BY preportno";
        return session.createQuery(hql, Preport.class).getResultList();
    }

    @Override
    public Preport getByPrimaryKey(Integer preportno) {
        return session.get(Preport.class, preportno);
    }

    @Override
    public List<Preport> getAll(Map<String, String[]> map) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Preport> criteriaQuery = builder.createQuery(Preport.class);
        Root<Preport> root = criteriaQuery.from(Preport.class);

        List<Predicate> predicateList = new ArrayList<>();

        Set<String> keys = map.keySet();
        int count = 0;
        for (String key : keys) {
            String value = map.get(key)[0]; //why[0]?
            //!"action".equals(key) 要加避免當成where條件傳進來 ?
            if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
                count++;
                predicateList.add(getPredicateForDB(builder, root, key, value));
            }
        }
        System.out.println("predicateList.size()=" + predicateList.size());
        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        Query query = session.createQuery(criteriaQuery);
        List<Preport> preportList = query.getResultList();
        return preportList;
    }

    public static Predicate getPredicateForDB(CriteriaBuilder builder, Root<Preport> root, String columnName, String value) {
        Predicate predicate = null;
        if ("preportstatus".equals(columnName)) {
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("pname".equals(columnName)) {
            predicate = builder.like(root.get("pcomment").get("product").get("pname"), "%" + value + "%");
        }
        return predicate;
    }

    @Override
    public PageResult<Preport> getAll(Map<String, String[]> map, Integer pageNumber, Integer pageSize) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Preport> criteriaQuery = builder.createQuery(Preport.class);
        Root<Preport> root = criteriaQuery.from(Preport.class);

        List<Predicate> predicateList = new ArrayList<>();

        Set<String> keys = map.keySet();
        int count = 0;
        for (String key : keys) {
            String value = map.get(key)[0];
            //!"action".equals(key) 要加避免當成where條件傳進來
            if (value != null && value.trim().length() != 0 && !"action".equals(key) && !"pageNumber".equals(key) && !"pageSize".equals(key)) {
                count++;
                predicateList.add(getPredicateForDB(builder, root, key, value));
            }
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        criteriaQuery.orderBy(builder.desc(root.get("preportno")));

        //取得總筆數
        Query countQuery = session.createQuery(criteriaQuery);
        Integer totalCount = countQuery.getResultList().size();

        //分頁
        Query query = session.createQuery(criteriaQuery);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Preport> preportList = query.getResultList();

        //封裝分頁結果
        PageResult<Preport> pageResult =new PageResult<>();
        pageResult.setTotalCount(totalCount);
        pageResult.setData(preportList);
        return pageResult;

    }
}
