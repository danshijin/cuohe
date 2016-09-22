package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChProductEntity;

public interface ChProductEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChProductEntity record);

    int insertSelective(ChProductEntity record);

    ChProductEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChProductEntity record);

    int updateByPrimaryKey(ChProductEntity record);
}