package com.smm.cuohe.bo.impl.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.cuohe.bo.users.IWebPartsPBO;
import com.smm.cuohe.dao.users.WebPartsPDAO;
import com.smm.cuohe.domain.WebPartsPOJO;

@Service
public class WebPartsPBoImpl implements IWebPartsPBO{
	
	@Resource
	private WebPartsPDAO wDao;

	@Override
	public List<WebPartsPOJO> getWebPartsPList(int userId) {
		List<WebPartsPOJO> wlist=wDao.getWebPartsPList(userId);
		return wlist;
	}

	@Override
	public Map<String, Object> addOrDelWpById(Map<String, Object> map) {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		//判断当前品目和webpartsid在数据库中是否存在
		WebPartsPOJO wp=wDao.getWpById(map);
		Integer num=0;
		if(wp ==null){
			num=wDao.insertWp(map);
		}else{
			num=wDao.addOrDelWpById(map);
		}
		if(num>0){
			resultMap.put("code", "succ");	
			resultMap.put("msg", "系统提示，操作成功");	
		}else{
			resultMap.put("code", "error");
			resultMap.put("msg", "系统提示，操作失败");	
		}
		
		return resultMap;
	}
}
