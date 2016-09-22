package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChModulesEntity;

public interface ChModulesEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChModulesEntity record);

    int insertSelective(ChModulesEntity record);

    ChModulesEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChModulesEntity record);

    int updateByPrimaryKey(ChModulesEntity record);
}