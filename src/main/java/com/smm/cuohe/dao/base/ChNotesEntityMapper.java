package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChNotesEntity;

public interface ChNotesEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChNotesEntity record);

    int insertSelective(ChNotesEntity record);

    ChNotesEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChNotesEntity record);

    int updateByPrimaryKey(ChNotesEntity record);
}