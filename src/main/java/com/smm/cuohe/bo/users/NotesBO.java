package com.smm.cuohe.bo.users;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smm.cuohe.domain.Notes;
import com.smm.cuohe.domain.User;

public interface NotesBO {

	/**
	 * 获取组内共享
	 */
	public Notes notesByOrgan(Map<String, Object> paramMap);
	
	/**
	 * 
	 * @param notes
	 * @return
	 */
	public Integer addnotesByOrgan(User user,Notes notes);
	
	
	/**
	 * 个人便签
	 */
	public Notes notesByMe(Map<String, Object> paramMap);
	
	/**
	 * 
	 * @param notes
	 * @return
	 */
	public Integer addnotesByMe(User user,Notes notes);
	
	
	/**
	 *
	 * @param user
	 * @return
	 */
	public Map<String, Object> recentlyTrend(User user);


	/**
	 * 获取指定管理员所有日志创建日期。返回结果按日期升序排列
	 * @param user_id
	 * @param item_id
	 */
	public List<String> getNotesCreateDateByUser(int user_id,int item_id);
}
