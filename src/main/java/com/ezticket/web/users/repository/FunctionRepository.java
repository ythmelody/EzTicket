package com.ezticket.web.users.repository;

import com.ezticket.web.users.pojo.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Integer>  {
    Function findByFuncno(Integer funcno);

}
