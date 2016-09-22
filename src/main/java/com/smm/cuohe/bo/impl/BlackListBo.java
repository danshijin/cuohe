package com.smm.cuohe.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.IBlacklistBO;
import com.smm.cuohe.dao.CustomsBlackListMapper;
import com.smm.cuohe.domain.CustomsBlackList;
import com.smm.cuohe.domain.CustomsBlackListDto;
@Service("blackListService")
public class BlackListBo extends BaseService implements IBlacklistBO {
	@Autowired CustomsBlackListMapper blackListDao;

	/**
	 * 添加黑名单
	 */
	@Override
	public CustomsBlackList addBlack(CustomsBlackList cuscomsBlackList) {
		// TODO Auto-generated method stub
		int a = 0;
		try {
		a = blackListDao.insert(cuscomsBlackList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return cuscomsBlackList;
		}
		return cuscomsBlackList;
	}

	/**
	 * 获取黑名单列表
	 */
	@Override
	public List<CustomsBlackListDto> queryBlackListByCustomerID(
			Integer customerId) {
		// TODO Auto-generated method stub
		List<CustomsBlackListDto> bllist = blackListDao.queryBlackList(customerId);
		List<CustomsBlackListDto> newbllist = new ArrayList<CustomsBlackListDto>();
		for (CustomsBlackListDto cuscomsBlackListDto : bllist) {
			String[] codes = cuscomsBlackListDto.getEntTypeCode().split(",");
			String code = "";
			CustomsBlackListDto dto = new CustomsBlackListDto();
			for (int i = 0; i < codes.length; i++) {
				dto.setEntTypeCode(codes[i]);
				if(i==0){
					code = blackListDao.getEntTypesByCode(dto);
				}else{
					code=code+","+blackListDao.getEntTypesByCode(dto);
				}
			}
			cuscomsBlackListDto.setEntTypes(code);
			newbllist.add(cuscomsBlackListDto);
		}
		return newbllist;
	}

	/**
	 * 删除黑名单
	 * 
	 */
	@Override
	public boolean deleteBlackListById(Integer id) {
		// TODO Auto-generated method stub
		return blackListDao.deleteBlackListById(id)>0;
	}

	@Override
	public CustomsBlackListDto queryBlackListByKey(Integer id) {
		// TODO Auto-generated method stub
		CustomsBlackListDto cuscomsBlackListDto =  blackListDao.queryBlackListById(id);
		String[] codes = cuscomsBlackListDto.getEntTypeCode().split(",");
		String code = "";
		CustomsBlackListDto dto = new CustomsBlackListDto();
		for (int i = 0; i < codes.length; i++) {
			dto.setEntTypeCode(codes[i]);
			if(i==0){
				code = blackListDao.getEntTypesByCode(dto);
			}else{
				code=code+","+blackListDao.getEntTypesByCode(dto);
			}
		}
		cuscomsBlackListDto.setEntTypes(code);
		return cuscomsBlackListDto;
	}

	/**
	 * check黑名单是否添加
	 */
	@Override
	public boolean checkBlack(CustomsBlackList cuscomsBlackList) {
		// TODO Auto-generated method stub
		return blackListDao.checkBlack(cuscomsBlackList)>0;
	}

}
