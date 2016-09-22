package com.smm.cuohe.dao.base;

import com.smm.cuohe.domain.base.ChChatSEntity;

public interface ChChatSEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChChatSEntity record);

    int insertSelective(ChChatSEntity record);

    ChChatSEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChChatSEntity record);

    int updateByPrimaryKey(ChChatSEntity record);
}