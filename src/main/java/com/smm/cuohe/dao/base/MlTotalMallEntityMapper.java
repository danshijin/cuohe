package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.MlTotalMallEntity;

public interface MlTotalMallEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MlTotalMallEntity record);

    int insertSelective(MlTotalMallEntity record);

    MlTotalMallEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MlTotalMallEntity record);

    int updateByPrimaryKey(MlTotalMallEntity record);
}