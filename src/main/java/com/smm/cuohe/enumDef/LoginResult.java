package com.smm.cuohe.enumDef;

/**
 * 
 * 登录结果
 * 
 * @author zengshihua
 *
 */
public enum LoginResult {

	SUCC("SUCC", "登录成功"),
	FAILD("FAILD", "用户名或密码错误"),
	EXE("EXE", "程序应用错误"),
	VCODE_ERROR("VCODE_ERROR","验证码错误"),
	VCODE_NULL("VCODE_NULL", "验证不能为空");

	private String code;
	private String message;

	private LoginResult(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
