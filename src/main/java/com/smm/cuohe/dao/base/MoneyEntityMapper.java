package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.MoneyEntity;

public interface MoneyEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MoneyEntity record);

    int insertSelective(MoneyEntity record);

    MoneyEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoneyEntity record);

    int updateByPrimaryKey(MoneyEntity record);
}