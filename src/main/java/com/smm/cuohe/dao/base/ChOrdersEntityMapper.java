package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChOrdersEntity;

public interface ChOrdersEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChOrdersEntity record);

    int insertSelective(ChOrdersEntity record);

    ChOrdersEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChOrdersEntity record);

    int updateByPrimaryKey(ChOrdersEntity record);
}