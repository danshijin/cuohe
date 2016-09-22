package com.smm.cuohe.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smm.cuohe.domain.User;

/**
 * 
* @ClassName: AopControllerLog 
* @Description: 访问日志AOP实现 
* @author youxiaoshuang
*
 */
@Aspect
public class AopControllerLog {
	private final Logger logger = Logger.getLogger(this.getClass());

	private String requestPath = null ; // 请求地址
	private String userName = null ; // 用户名
//	private Map<?,?> inputParamMap = null ; // 传入参数
	private Map<String, Object> outputParamMap = null; // 存放输出结果
	private Object[] restTemplateArgs;
	private Gson gson = new GsonBuilder().disableInnerClassSerialization().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	/**
	 * 执行前调用
	 * @param joinPoint
	 */
	@Before("execution(* org.springframework.web.client..*.*(..))")
	public void doBeforeInServiceLayer(JoinPoint joinPoint) {
		
	}

	/**
	 * 执行后调用
	 * @param joinPoint
	 */
	@After("execution(* org.springframework.web.client..*.*(..))")
	public void doAfterInServiceLayer(JoinPoint joinPoint) {
		this.printOptLog();
	}

	/**
	 * 环绕调用
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* org.springframework.web.client..*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		//获取request信息
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes)ra;
		HttpServletRequest request = sra.getRequest();
//		 从session中获取用户信息
		User user = (User) request.getSession().getAttribute("userInfo");
		if(user != null && !"".equals(user)){
			userName = user.getName().toString();
		}else{
			userName = "未登录" ;
		}
		// 获取输入参数
//		inputParamMap = request.getParameterMap();
		// 获取请求地址
		requestPath = request.getRequestURI();
		buildInterfaceParams(pjp.getArgs());
		// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
		outputParamMap = new HashMap<String, Object>();
		Object result = pjp.proceed();// result的值就是被拦截方法的返回值
		outputParamMap.put("result", result);
		
		return result;
	}

	private void buildInterfaceParams(Object[] argsArr){
		List<Object> list = new ArrayList<>();
		for(int i =0;i < argsArr.length; i++) {
			if(argsArr[i] instanceof java.lang.Class<?>) {
				continue;
			}
			if(argsArr[i] instanceof Object[] 
					&& argsArr[i] != null 
					&& ((Object[]) argsArr[i]).length > 0 
					&& ((Object[]) argsArr[i])[0] instanceof java.lang.Class<?>) {
				continue;
			}
			list.add(argsArr[i]);
		}
		restTemplateArgs = list.toArray();
	}
	
	/**
	 * 
	 * @Title：printOptLog
	 * @Description: 输出日志 
	 * @author youxiaoshuang
	 */
	private void printOptLog() {
		logger.info("\n**" + userName + ", requestUrl："+requestPath 
				+ "\n**接口参数："+gson.toJson(restTemplateArgs)
				+ "\n**接口返回值："+gson.toJson(outputParamMap));
	}
}
