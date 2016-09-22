package com.smm.cuohe.bo;

import java.util.List;

import com.smm.cuohe.domain.CustomsBlackList;
import com.smm.cuohe.domain.CustomsBlackListDto;

/**
 * 黑名单管理
 * @author youxiaoshuang
 *
 */
public interface IBlacklistBO {
	//添加黑名单
	public CustomsBlackList addBlack(CustomsBlackList cuscomsBlackList);
	//查询黑名单列表
	public List<CustomsBlackListDto> queryBlackListByCustomerID(Integer customerId);
	//删除黑名单
	public boolean deleteBlackListById(Integer id);
	//根据Id查询黑名单
	public CustomsBlackListDto queryBlackListByKey(Integer id);
	
	public boolean checkBlack(CustomsBlackList cuscomsBlackList);

}
