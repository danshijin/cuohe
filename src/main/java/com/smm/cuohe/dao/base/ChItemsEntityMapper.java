package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChItemsEntity;

public interface ChItemsEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChItemsEntity record);

    int insertSelective(ChItemsEntity record);

    ChItemsEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChItemsEntity record);

    int updateByPrimaryKey(ChItemsEntity record);
}