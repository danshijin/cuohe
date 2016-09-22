package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChComRecordSEntity;

public interface ChComRecordSEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChComRecordSEntity record);

    int insertSelective(ChComRecordSEntity record);

    ChComRecordSEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChComRecordSEntity record);

    int updateByPrimaryKey(ChComRecordSEntity record);
}