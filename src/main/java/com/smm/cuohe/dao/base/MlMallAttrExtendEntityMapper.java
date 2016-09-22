package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.MlMallAttrExtendEntity;

public interface MlMallAttrExtendEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MlMallAttrExtendEntity record);

    int insertSelective(MlMallAttrExtendEntity record);

    MlMallAttrExtendEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MlMallAttrExtendEntity record);

    int updateByPrimaryKey(MlMallAttrExtendEntity record);
}