package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChItemAttrEntity;

public interface ChItemAttrEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChItemAttrEntity record);

    int insertSelective(ChItemAttrEntity record);

    ChItemAttrEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChItemAttrEntity record);

    int updateByPrimaryKeyWithBLOBs(ChItemAttrEntity record);

    int updateByPrimaryKey(ChItemAttrEntity record);
}