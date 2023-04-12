package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.dto.BlockPriceDto;
import com.ezticket.web.activity.pojo.BlockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockPriceRepository extends JpaRepository <BlockPrice,Integer>{

//    Add by Shawn on 04/08
    public BlockPrice findByBlockNo(Integer blockNo);

//    Add by Shawn on 04/11
    public List<BlockPrice> findByActivityNo(int activityNo);
}
