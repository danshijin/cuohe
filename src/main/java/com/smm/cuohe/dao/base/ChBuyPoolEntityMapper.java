package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChBuyPoolEntity;

public interface ChBuyPoolEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChBuyPoolEntity record);

    int insertSelective(ChBuyPoolEntity record);

    ChBuyPoolEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChBuyPoolEntity record);

    int updateByPrimaryKey(ChBuyPoolEntity record);
}