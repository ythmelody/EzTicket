package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.CollectVO;
import com.ezticket.web.activity.pojo.TicketHolder;
import com.ezticket.web.activity.repository.CollectDAO;
import com.ezticket.web.activity.repository.TicketHolderDAO;
import com.ezticket.web.activity.repository.impl.CollectDAOImpl;
import com.ezticket.web.activity.repository.impl.TicketHolderDAOImpl;

import java.util.List;

public class CollectService {
    private CollectDAO cdao;
    private TicketHolderDAO thdao;

    public CollectService() {

        cdao = new CollectDAOImpl();
        thdao = new TicketHolderDAOImpl();

    }


//    public EmpVO addEmp(String ename, String job, java.sql.Date hiredate,
//                        Double sal, Double comm, Integer deptno) {
//
//        EmpVO empVO = new EmpVO();
//
//        empVO.setEname(ename);
//        empVO.setJob(job);
//        empVO.setHiredate(hiredate);
//        empVO.setSal(sal);
//        empVO.setComm(comm);
//        empVO.setDeptno(deptno);
//        dao.insert(empVO);
//
//        return empVO;
//    }
//
//    //預留給 Struts 2 或 Spring MVC 用
//    public void addEmp(EmpVO empVO) {
//        dao.insert(empVO);
//    }

//    欠會員的修改功能
//    public CollectVO updateCollect(Integer collectno, String memail) {
//
//        EmpVO empVO = new EmpVO();
//
//        empVO.setEmpno(empno);
//        empVO.setEname(ename);
//        empVO.setJob(job);
//        empVO.setHiredate(hiredate);
//        empVO.setSal(sal);
//        empVO.setComm(comm);
//        empVO.setDeptno(deptno);
//        dao.update(empVO);
//
//        return dao.findByPrimaryKey(empno);
//    }


    public TicketHolder getOneTicket(Integer collectno) {
        return thdao.findByCollectno(collectno);
    }

    public List<TicketHolder> getAll() {
        return thdao.getAll();
    }
}
