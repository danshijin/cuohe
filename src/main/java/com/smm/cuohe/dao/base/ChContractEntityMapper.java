package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChContractEntity;

public interface ChContractEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChContractEntity record);

    int insertSelective(ChContractEntity record);

    ChContractEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChContractEntity record);

    int updateByPrimaryKeyWithBLOBs(ChContractEntity record);

    int updateByPrimaryKey(ChContractEntity record);
}