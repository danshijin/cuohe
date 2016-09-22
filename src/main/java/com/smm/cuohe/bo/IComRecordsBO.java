package com.smm.cuohe.bo;

import com.smm.cuohe.domain.ComRecords;
import com.smm.cuohe.domain.User;

import java.util.Map;

public interface IComRecordsBO {

	/** 通过客户id获取分页后的沟通记录
	 * @param customerId
	 * @return
	 */
	public abstract Map<String, Object> getPagingRecordsByCustomerId(int customerId, int start, int len);

	/** 添加沟通记录
	 * @param companyId
	 * @return
	 */
	public abstract String addComRecord(String newRecordTitle, String newRecordContext, User user, Integer customerId);
	
	
	/** 更新沟通记录
	 * @param record
	 * @param u
	 * @return
	 */
	public abstract String updateComRecord(ComRecords record, User u);
	
	/** 删除沟通记录
	 * @param id
	 * @return
	 */
	public abstract String deleteComRecord(Integer id);

	
	

}