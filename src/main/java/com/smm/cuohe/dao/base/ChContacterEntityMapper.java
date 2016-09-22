package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChContacterEntity;

public interface ChContacterEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChContacterEntity record);

    int insertSelective(ChContacterEntity record);

    ChContacterEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChContacterEntity record);

    int updateByPrimaryKey(ChContacterEntity record);
}