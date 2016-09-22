package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChWareHouse;

public interface ChWareHouseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChWareHouse record);

    int insertSelective(ChWareHouse record);

    ChWareHouse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChWareHouse record);

    int updateByPrimaryKey(ChWareHouse record);
}