package com.ezticket.web.users.repository;

import com.ezticket.web.users.pojo.Roleauthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleauthorityRepository extends JpaRepository<Roleauthority, Integer>  {
    List<Roleauthority> findByRoleno(Integer roleno);


}
