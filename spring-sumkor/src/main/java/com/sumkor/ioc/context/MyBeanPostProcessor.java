package com.sumkor.ioc.context;

import com.sumkor.ioc.bean.MyBeanLife;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author Sumkor
 * @since 2020/6/15
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

	/**
	 * 容器加载的时候会加载一些其他的bean，会调用初始化前和初始化后方法
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof MyBeanLife) {
			System.out.println("MyBeanPostProcessor.postProcessBeforeInitialization");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof MyBeanLife) {
			System.out.println("MyBeanPostProcessor.postProcessAfterInitialization");
		}
		return bean;
	}
}
