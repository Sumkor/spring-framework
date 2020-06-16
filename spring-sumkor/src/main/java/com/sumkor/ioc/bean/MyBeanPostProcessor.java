package com.sumkor.ioc.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 自定义 BeanPostProcessor
 * 容器加载的时候会加载一些其他的bean，会调用初始化前和初始化后方法
 *
 * @author Sumkor
 * @since 2020/6/15
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

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
