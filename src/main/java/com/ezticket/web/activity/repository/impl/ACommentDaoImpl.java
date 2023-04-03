package com.ezticket.web.activity.repository.impl;

import com.ezticket.web.activity.pojo.AComment;
import com.ezticket.web.activity.repository.ACommentDao;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class ACommentDaoImpl implements ACommentDao {

    @PersistenceContext
    private Session session;

    @Override
    public List<AComment> getByCompositeQuery(Map map) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ac.* FROM acomment ac " +
                "JOIN activity act on ac.activityno = act.activityno ");

        Set<String> keys = map.keySet();
        int count = 0;
        for (String key : keys) {
            String value = (String) map.get(key);
            System.out.println(key + "=" + value);
            if(!(value == null || value.trim().length() == 0 || value.trim().equals("all"))) {
                count++;
                if (count == 1){
                    if(key.equals("activityno")){
                        sql.append(" WHERE act." + key + " = " + value + " ");
                    } else {
                        sql.append(" WHERE ac." + key + " = " + value + " ");
                    }
                } else {
                    if(key.equals("activityno")){
                        sql.append(" AND act." + key + " = " + value + " ");
                    } else {
                        sql.append(" AND ac." + key + " = " + value + " ");
                    }
                }
            }
        }

        System.out.println(sql);

        return session.createNativeQuery(sql.toString(), AComment.class).getResultList();
    }
}
