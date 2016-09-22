package com.smm.cuohe.logo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import freemarker.log.Logger;

public class LogInvoHandler implements InvocationHandler{
	
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    private Object target; // 代理目标
    
    private Object proxy; // 代理对象
    
    private static HashMap<Class<?>, LogInvoHandler> invoHandlers = new HashMap<Class<?>, LogInvoHandler>();

    private LogInvoHandler() {
    	
    }

    /**
     * 通过Class来生成动态代理对象Proxy
     * @param clazz
     * @return
     */
    public synchronized static<T> T getProxyInstance(Class<T> clazz){
        LogInvoHandler invoHandler = invoHandlers.get(clazz);

        if(null == invoHandler){
            invoHandler = new LogInvoHandler();
            try {
                T tar = clazz.newInstance();
                invoHandler.setTarget(tar);
                invoHandler.setProxy(Proxy.newProxyInstance(tar.getClass().getClassLoader(),tar.getClass().getInterfaces(), invoHandler));
            } catch (Exception e) {
                e.printStackTrace();
            }
            invoHandlers.put(clazz, invoHandler);
        }
        return (T)invoHandler.getProxy();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    	
        Object result = method.invoke(target, args); // 执行业务处理
        
        // 打印日志
        logger.info("return: " + result);
        
        return result;
    }
    
    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

}