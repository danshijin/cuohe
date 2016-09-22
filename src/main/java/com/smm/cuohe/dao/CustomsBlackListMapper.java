package com.smm.cuohe.dao;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.CustomsBlackList;
import com.smm.cuohe.domain.CustomsBlackListDto;


public interface CustomsBlackListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomsBlackList record);

    int insertSelective(CustomsBlackList record);

    CustomsBlackList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomsBlackList record);

    int updateByPrimaryKey(CustomsBlackList record);
    
    List<CustomsBlackListDto> queryBlackList(Integer customerId);
    //根据code查企业类型
    String getEntTypesByCode(CustomsBlackListDto cuscomsBlackListDto);
    //根据Id查黑名单
    CustomsBlackListDto queryBlackListById(Integer id);

	int deleteBlackListById(Integer id);
    //黑名单是否存在
	int checkBlack(CustomsBlackList cuscomsBlackList);

	/** 黑名单个数
	 * @Title: checkBlacklist 
	 * @Description: TODO
	 * @param param
	 * @return
	 * @return: List<CustomsBlackList>
	 */
	List<CustomsBlackList> checkBlacklist(Map<String, Object> param);
    
    
}