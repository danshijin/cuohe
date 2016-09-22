package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChDiscussChatRecordEntity;

public interface ChDiscussChatRecordEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChDiscussChatRecordEntity record);

    int insertSelective(ChDiscussChatRecordEntity record);

    ChDiscussChatRecordEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChDiscussChatRecordEntity record);

    int updateByPrimaryKey(ChDiscussChatRecordEntity record);
}