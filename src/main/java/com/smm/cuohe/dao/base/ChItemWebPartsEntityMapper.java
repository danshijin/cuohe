package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChItemWebPartsEntity;

public interface ChItemWebPartsEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChItemWebPartsEntity record);

    int insertSelective(ChItemWebPartsEntity record);

    ChItemWebPartsEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChItemWebPartsEntity record);

    int updateByPrimaryKey(ChItemWebPartsEntity record);
}