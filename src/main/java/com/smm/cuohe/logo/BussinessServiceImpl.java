package com.smm.cuohe.logo;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BussinessServiceImpl implements BussinessService {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public String intput(String methodName, String parameter) {
		String str="调用方法名："+methodName+"---输入参数："+parameter+"---请求时间："+sdf.format(new Date());
		return str;
	}

	@Override
	public String retur(String parameter) {
		String str="返回值："+parameter+"---返回时间："+sdf.format(new Date());
		return str;
	}
	
}