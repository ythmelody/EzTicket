package com.ezticket.web.users.repository;

import com.ezticket.web.users.pojo.Backuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackuserRepository extends JpaRepository<Backuser, Integer> {

    Backuser findByBaaccount(String baacount);

    Backuser findByBaemail(String email);
}
