package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChSellPoolEntity;

public interface ChSellPoolEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChSellPoolEntity record);

    int insertSelective(ChSellPoolEntity record);

    ChSellPoolEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChSellPoolEntity record);

    int updateByPrimaryKey(ChSellPoolEntity record);
}