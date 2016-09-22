package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChBuyingAttrsEntity;

public interface ChBuyingAttrsEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChBuyingAttrsEntity record);

    int insertSelective(ChBuyingAttrsEntity record);

    ChBuyingAttrsEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChBuyingAttrsEntity record);

    int updateByPrimaryKey(ChBuyingAttrsEntity record);
}