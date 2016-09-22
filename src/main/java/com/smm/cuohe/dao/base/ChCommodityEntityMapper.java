package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChCommodityEntity;

public interface ChCommodityEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChCommodityEntity record);

    int insertSelective(ChCommodityEntity record);

    ChCommodityEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChCommodityEntity record);

    int updateByPrimaryKey(ChCommodityEntity record);
}