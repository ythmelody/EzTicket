package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Aclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AclassRepository extends JpaRepository<Aclass,Integer> {
}
