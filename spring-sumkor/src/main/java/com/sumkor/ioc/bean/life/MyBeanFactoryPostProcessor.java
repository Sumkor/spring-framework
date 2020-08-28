package com.sumkor.ioc.bean.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 当Spring将BeanFactory组建完成之后，使用后置处理器去操作BeanFactory
 * BeanFactory组建完成的标识，是Spring扫描完包路径之后，将扫描的的对象解析成BeanDefinition，并放置到了BeanFactory之中的beanDefinitionMap
 *
 * @author Sumkor
 * @since 2020/8/26
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("myBeanLife");
		// 可在这里对myBeanLife的beanDefinition进行修改
		System.out.println("MyBeanFactoryPostProcessor.postProcessBeanFactory");
	}
}
