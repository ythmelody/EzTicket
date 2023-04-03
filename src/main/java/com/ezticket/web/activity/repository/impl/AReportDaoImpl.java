package com.ezticket.web.activity.repository.impl;

import com.ezticket.web.activity.pojo.AReport;
import com.ezticket.web.activity.repository.AReportDao;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Repository
public class AReportDaoImpl implements AReportDao {

    @PersistenceContext
    private Session session;
    @Override
    public List<AReport> getByCompositeQuery(Map map) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ar.* FROM areport ar " +
                "JOIN acomment ac ON ar.acommentno = ac.acommentno " +
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
                        sql.append(" WHERE ar." + key + " = " + value + " ");
                    }
                } else {
                    if(key.equals("activityno")){
                        sql.append(" AND act." + key + " = " + value + " ");
                    } else {
                        sql.append(" AND ar." + key + " = " + value + " ");
                    }
                }
            }
        }

        return session.createNativeQuery(sql.toString(), AReport.class).getResultList();
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;



//        try {
//            con = ds.getConnection();
//            String finalSQL = "SELECT ar.* FROM areport ar"
//                    + "JOIN acomment ac ON ac.acommentno = ar.acommentno"
//                    + "JOIN activity act ON act.activityno = ac. activityno"
//                    + CompositeQueryForAReport.get_WhereCondition(map)
//                    + "order by empno";
//            pstmt = con.prepareStatement(finalSQL);
//            System.out.println("●●finalSQL(by DAO) = "+finalSQL);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                aReport = new AReport();
//                aReport.setAReportNo(rs.getInt("areportno"));
//                aReport.setACommentNo(rs.getInt("acommentno"));
//                aReport.setMemberNo(rs.getInt("memberno"));
//                aReport.setAWhy(rs.getString("awhy"));
//                aReport.setAReportStatus(rs.getInt("areportstatus"));
//                aReport.setAReportDate(rs.getDate("areportdate"));
//                list.add(aReport); // Store the row in the List
//            }
//
//            // Handle any SQL errors
//        } catch (SQLException se) {
//            throw new RuntimeException("A database error occured. "
//                    + se.getMessage());
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException se) {
//                    se.printStackTrace(System.err);
//                }
//            }
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                } catch (SQLException se) {
//                    se.printStackTrace(System.err);
//                }
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (Exception e) {
//                    e.printStackTrace(System.err);
//                }
//            }
//        }
    }

//    public static void main(String[] args) {
//        AReportDaoImpl aReportRepositoryImp = new AReportDaoImpl();
//        Map<String, String> map = new TreeMap<String, String>();
//        map.put("activityno", "all");
//        map.put("areportstatus", "1");
//        System.out.println("map為:" + map);
//        aReportRepositoryImp.getByCompositeQuery(map);
//        System.out.println("成功了!!!");
//    }
}
