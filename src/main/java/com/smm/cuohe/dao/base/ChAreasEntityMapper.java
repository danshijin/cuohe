package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChAreasEntity;

public interface ChAreasEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChAreasEntity record);

    int insertSelective(ChAreasEntity record);

    ChAreasEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChAreasEntity record);

    int updateByPrimaryKey(ChAreasEntity record);
}