package com.smm.cuohe.bo.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IComRecordsBO;
import com.smm.cuohe.dao.IComRecordsDAO;
import com.smm.cuohe.domain.ComRecords;
import com.smm.cuohe.domain.User;

@Service
public class ComRecordsBO implements IComRecordsBO {
	
	@Resource
	IComRecordsDAO iComRecordsDao;
	
	
	/* (non-Javadoc)
	 * @see com.smm.cuohe.bo.impl.IComRecordsBO#getRecordsByCompanyId(java.lang.Integer)
	 */
	@Override
	public Map<String, Object> getPagingRecordsByCustomerId(int customerId, int start, int len){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", iComRecordsDao.getPagingRecordsByCustomerId(customerId, start, len));
		map.put("total", iComRecordsDao.getRecordsCountByCustomerId(customerId));
		return map;
	}

	@Override
	public String addComRecord(String newRecordTitle, String newRecordContext, User user, Integer customerId) {
		ComRecords record = new ComRecords();
		
		record.setItemId(Integer.parseInt(user.getItemId()));
		record.setCustomerId(customerId);
		record.setCreatedBy(user.getId());
		record.setTitle(newRecordTitle);
		record.setContext(newRecordContext);
		
		iComRecordsDao.insertRecord(record);
		return "success";
	}

	@Override
	public String updateComRecord(ComRecords record, User u) {
		
		record.setUpdatedBy(u.getId());
		
		iComRecordsDao.updateComRecord(record);
		return "success";
		
	}

	@Override
	public String deleteComRecord(Integer id) {
		
		iComRecordsDao.deleteComRecord(id);
		return "success";
	}
}
