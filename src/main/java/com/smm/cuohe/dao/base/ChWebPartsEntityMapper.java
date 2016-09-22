package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChWebPartsEntity;

public interface ChWebPartsEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChWebPartsEntity record);

    int insertSelective(ChWebPartsEntity record);

    ChWebPartsEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChWebPartsEntity record);

    int updateByPrimaryKey(ChWebPartsEntity record);
}