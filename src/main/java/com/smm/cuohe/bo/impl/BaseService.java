package com.smm.cuohe.bo.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

public class BaseService {
	private Logger log = Logger.getLogger(this.getClass());
	public <T> T copy(Object s,Class<T> t) throws Exception{
		try{
			T tn = t.newInstance();
			BeanUtils.copyProperties(s, tn);
			return tn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
