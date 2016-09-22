package com.smm.cuohe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smm.cuohe.domain.ComRecords;

public interface IComRecordsDAO {
	
	/** 获取企业id为companyId的，从start处开始取len长度的数据
	 * @param companyId
	 * @param start
	 * @param len
	 * @return
	 */
	public List<ComRecords> getPagingRecordsByCustomerId(@Param("customerId") int customerId, @Param("start") int start, @Param("len") int len);
	
	/** 查询对应企业id的沟通记录总数
	 * @param companyId
	 * @return
	 */
	public int getRecordsCountByCustomerId(int companyId);
	
	/** 插入沟通记录
	 * @param record
	 */
	public void insertRecord(ComRecords record);

	/** 更新沟通记录
	 * @param record
	 */
	public void updateComRecord(ComRecords record);
	
	/** 删除沟通记录
	 * @param id
	 */
	public void deleteComRecord(int id);
	
}
