package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChSubOrdersEntity;

public interface ChSubOrdersEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChSubOrdersEntity record);

    int insertSelective(ChSubOrdersEntity record);

    ChSubOrdersEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChSubOrdersEntity record);

    int updateByPrimaryKey(ChSubOrdersEntity record);
}