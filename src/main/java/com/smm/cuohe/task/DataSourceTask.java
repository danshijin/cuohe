package com.smm.cuohe.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
/**
 * 防止连接池超时
 */
import com.smm.cuohe.bo.IUserBO;

/**
 * 
 * @author youxiaoshuang
 * 
 */
public class DataSourceTask implements ApplicationListener<ContextRefreshedEvent> {
//	private Logger logger = Logger.getLogger(DataSourceTask.class);
	@Autowired
	private IUserBO userBo;

	/**
	 * 项目启动后执行
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		if (event.getApplicationContext().getParent() == null) {
			timeTask();
		}

	}

	private void timeTask() {
		Runnable runable = new Runnable() {
			public void run() {
				taskService();
			}
		};
		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runable, 10, 10, TimeUnit.MINUTES);// 十分钟刷一次
	}

	private void taskService() {
		// 与数据库保持连接
		int i = userBo.jobTask();
	}
}
