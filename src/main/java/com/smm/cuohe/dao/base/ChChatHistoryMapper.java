package com.smm.cuohe.dao.base;



import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.base.ChChatHistory;

public interface ChChatHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChChatHistory record);

    int insertSelective(ChChatHistory record);

    ChChatHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChChatHistory record);

    int updateByPrimaryKey(ChChatHistory record);
    
    ChChatHistory selectByNid(@Param(value="nid")Integer nid);
}