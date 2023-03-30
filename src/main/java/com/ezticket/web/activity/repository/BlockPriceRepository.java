package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.dto.BlockPriceDto;
import com.ezticket.web.activity.pojo.BlockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockPriceRepository extends JpaRepository <BlockPrice,Integer>{

}
