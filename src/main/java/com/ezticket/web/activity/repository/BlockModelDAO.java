package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.BlockModelVO;

import java.util.List;

public interface BlockModelDAO {

    void insert(BlockModelVO blockModelVO);

    void update(BlockModelVO blockModelVO);

    void delete(Integer blockno);

    BlockModelVO findByPK(Integer blockno);

    List<BlockModelVO> getAll();
}
