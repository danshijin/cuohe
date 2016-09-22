package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChCustomsEntity;

public interface ChCustomsEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChCustomsEntity record);

    int insertSelective(ChCustomsEntity record);

    ChCustomsEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChCustomsEntity record);

    int updateByPrimaryKey(ChCustomsEntity record);
}