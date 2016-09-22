package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChCustomsBlackListEntity;

public interface ChCustomsBlackListEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChCustomsBlackListEntity record);

    int insertSelective(ChCustomsBlackListEntity record);

    ChCustomsBlackListEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChCustomsBlackListEntity record);

    int updateByPrimaryKey(ChCustomsBlackListEntity record);
}