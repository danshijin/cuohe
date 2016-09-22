package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChSysNoticeEntity;

public interface ChSysNoticeEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChSysNoticeEntity record);

    int insertSelective(ChSysNoticeEntity record);

    ChSysNoticeEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChSysNoticeEntity record);

    int updateByPrimaryKey(ChSysNoticeEntity record);
}