package com.smm.cuohe.logo;


import org.springframework.beans.factory.annotation.Value;

import freemarker.log.Logger;


/**
 * 打印日志信息
 * @author wanghui
 *
 */
public class JournalPrint {
	
    public static Logger logger = Logger.getLogger(JournalPrint.class.getSimpleName());
    
    
    @Value("#{ch['addCompany.URL']}")
	private String addCompany;
    
    public static void main(String[] args) {
    	 BussinessService bs = LogInvoHandler.getProxyInstance(BussinessServiceImpl.class);
    	 
         String intput=bs.intput("print", "{1,2,3}");
 		 String retur=bs.retur("[1-2-3]");
 		 System.out.println(intput+"---"+retur);
 		 
	}
    
   /* public static void print() {
         BussinessService bs = LogInvoHandler.getProxyInstance(BussinessServiceImpl.class);
        
         String intput=bs.intput("print", "{1,2,3}");
 		 String retur=bs.retur("[1-2-3]");
 		 System.out.println(intput+"---"+retur);
    }*/
    
}