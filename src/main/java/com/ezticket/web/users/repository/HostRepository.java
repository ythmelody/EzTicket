package com.ezticket.web.users.repository;

import com.ezticket.web.users.pojo.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, Integer> {
}
