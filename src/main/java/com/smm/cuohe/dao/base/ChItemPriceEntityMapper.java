package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChItemPriceEntity;

public interface ChItemPriceEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChItemPriceEntity record);

    int insertSelective(ChItemPriceEntity record);

    ChItemPriceEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChItemPriceEntity record);

    int updateByPrimaryKey(ChItemPriceEntity record);
}