package com.smm.cuohe.dao.users;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.Notes;
import com.smm.cuohe.domain.User;


public interface NotesDAO {
	
	

	/**
	 * 获取组内共享
	 */
	public List<Notes> notesByOrgan(Map<String, Object> paramMap);
	
	
	
	/**
	 * 个人便签
	 */
	public List<Notes> notesByMe(Map<String, Object> paramMap);
	
	
	/**
	 *  新增便签
	 * @param notes
	 * @return
	 */
	public Integer addNotes(Notes notes);
	
	
	/**
	 * 修改记录
	 * 
	 */
	public Integer updateNotes(Notes notes);


	/**
	 * 获取指定管理员所有日志创建日期。返回结果按日期升序排列
	 * @param user_id
	 * @param item_id
	 */
	public List<String> getNotesCreateDateByUser(int user_id,int item_id);
	

	
}
