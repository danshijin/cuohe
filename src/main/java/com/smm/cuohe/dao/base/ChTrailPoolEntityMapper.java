package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChTrailPoolEntity;

public interface ChTrailPoolEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChTrailPoolEntity record);

    int insertSelective(ChTrailPoolEntity record);

    ChTrailPoolEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChTrailPoolEntity record);

    int updateByPrimaryKey(ChTrailPoolEntity record);
}