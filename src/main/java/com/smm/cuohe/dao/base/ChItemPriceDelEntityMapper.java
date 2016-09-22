package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChItemPriceDelEntity;

public interface ChItemPriceDelEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChItemPriceDelEntity record);

    int insertSelective(ChItemPriceDelEntity record);

    ChItemPriceDelEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChItemPriceDelEntity record);

    int updateByPrimaryKey(ChItemPriceDelEntity record);
}