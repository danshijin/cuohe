package com.smm.cuohe.dao.base;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.base.ChOnlineHistory;

public interface ChOnlineHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChOnlineHistory record);

    int insertSelective(ChOnlineHistory record);

    ChOnlineHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChOnlineHistory record);

    int updateByPrimaryKey(ChOnlineHistory record);
    /**
     * 查询指定日期内的登录列表
     * @param employeeId
     * @param currentDate
     * @return
     */
    List<ChOnlineHistory> queryOnlineTime(@Param(value="employeeId")int employeeId,@Param(value="startDate")Date startDate,@Param(value="endDate")Date endDate);
    
    /**
     * 查询该用户客户端最后一条登录记录
     */
    ChOnlineHistory queryLastHistoryClient(@Param(value="employeeId")int employeeId);
    
    ChOnlineHistory queryLastHistoryWeb(@Param(value="employeeId")int employeeId);
}