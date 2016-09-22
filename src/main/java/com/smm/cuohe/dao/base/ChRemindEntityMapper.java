package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChRemindEntity;

public interface ChRemindEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChRemindEntity record);

    int insertSelective(ChRemindEntity record);

    ChRemindEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChRemindEntity record);

    int updateByPrimaryKey(ChRemindEntity record);
}