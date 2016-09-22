package com.smm.cuohe.bo.users;

import java.util.List;

import com.smm.cuohe.domain.CustomsPOJO;

public interface ICustomsPojoBO {
	/**
	 * 最新加入客户
	 */
	public List<CustomsPOJO> getCustomsList(String itemsID);
}
