package com.smm.cuohe.dao.base;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.base.ChUsersEntity;
import com.smm.cuohe.domain.counts.ChatCount;

public interface ChUsersEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChUsersEntity record);

    int insertSelective(ChUsersEntity record);

    ChUsersEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChUsersEntity record);

    int updateByPrimaryKey(ChUsersEntity record);

	List<ChUsersEntity> queryUserList(Map<String, Object> map);
	
	List<ChUsersEntity> queryUserListPage(ChatCount chatCount);
	
	List<ChUsersEntity> queryUserListOrderByOff();
	
}