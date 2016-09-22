package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChCategoryEntity;

public interface ChCategoryEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChCategoryEntity record);

    int insertSelective(ChCategoryEntity record);

    ChCategoryEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChCategoryEntity record);

    int updateByPrimaryKey(ChCategoryEntity record);
}