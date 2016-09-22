package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChTrailContractEntity;

public interface ChTrailContractEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChTrailContractEntity record);

    int insertSelective(ChTrailContractEntity record);

    ChTrailContractEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChTrailContractEntity record);

    int updateByPrimaryKey(ChTrailContractEntity record);
}