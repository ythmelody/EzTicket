package com.ezticket.web.users.repository;

import com.ezticket.web.users.pojo.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByMemail(String memail);
}
