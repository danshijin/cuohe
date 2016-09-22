package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChItemProductsEntity;

public interface ChItemProductsEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChItemProductsEntity record);

    int insertSelective(ChItemProductsEntity record);

    ChItemProductsEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChItemProductsEntity record);

    int updateByPrimaryKey(ChItemProductsEntity record);
}