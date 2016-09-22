package com.smm.cuohe.bo.users;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.WebPartsPOJO;

public interface IWebPartsPBO {
	/**
	 * 最新加入客户
	 */
	public List<WebPartsPOJO> getWebPartsPList(int itemId);
	/**
	 * 删除组件
	 */
	public Map<String, Object> addOrDelWpById(Map<String, Object> map);
}
