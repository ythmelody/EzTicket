package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Seats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatsRepository  extends JpaRepository<Seats, Integer> {
}
