package com.smm.cuohe.util;

import java.io.Serializable;

/**
 * Created by zhenghao on 2015/8/13.
 * 接口返回对象封装
 */
public class RestResult implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1409738113179942640L;

	//处理成功返回 code
    public static final int SUCCESS_CODE=1;

    //处理失败返回 code
    public static final int ERROR_CODE=0;


    //返回信息状态编号 1 成功，0 失败. 默认0
    private int code=SUCCESS_CODE;

    //异常信息.默认空字符串
    private String errorMsg="";

    //返回数据结果集。默认 null
    private Object data=null;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
