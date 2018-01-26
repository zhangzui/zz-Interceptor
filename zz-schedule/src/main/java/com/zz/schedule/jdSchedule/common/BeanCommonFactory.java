package com.zz.schedule.jdSchedule.common;

import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class BeanCommonFactory {
	
	private static BeanCommonFactory beanFactory;
	
	private Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
	
	public static ApplicationContext applicationContext;

	private BeanCommonFactory(){
	}
	
	public static BeanCommonFactory getInstance(){
		if(beanFactory==null){
			beanFactory=new BeanCommonFactory();
		}
		return beanFactory;
	}
	
	public static void setApplicationContext(ApplicationContext context){
		applicationContext=context;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName, Class<T> type) {
		T t = null;
		if (map.get(type) == null) {
			if(applicationContext!=null){
				t =(T) applicationContext.getBean(beanName);
				return t;
			}
		} else {
			t = (T) map.get(type);
		}
		return t;
	}

	public Object getTheSingletonObject(String beanName){
		return applicationContext.getAutowireCapableBeanFactory().getBean(beanName);
	}
}
