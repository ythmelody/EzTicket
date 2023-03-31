package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Integer> {

}
