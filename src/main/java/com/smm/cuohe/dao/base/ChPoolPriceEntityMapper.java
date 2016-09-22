package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChPoolPriceEntity;

public interface ChPoolPriceEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChPoolPriceEntity record);

    int insertSelective(ChPoolPriceEntity record);

    ChPoolPriceEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChPoolPriceEntity record);

    int updateByPrimaryKey(ChPoolPriceEntity record);
}