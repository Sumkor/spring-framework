package com.sumkor.ioc.bean.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Sumkor
 * @since 2020/6/15
 */
public class MyBeanLife implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

	public MyBeanLife() {
		System.out.println("MyBeanLife.Constructor");
	}

	public void sayHello() {
		System.out.println("MyBeanLife.sayHello:哇哦");
	}

	/**
	 * 获取当前对象在Spring中的bean name
	 *
	 * @param name the name of the bean in the factory.
	 * Note that this name is the actual bean name used in the factory, which may
	 * differ from the originally specified name: in particular for inner bean
	 * names, the actual bean name might have been made unique through appending
	 * "#..." suffixes. Use the {@link BeanFactoryUtils#originalBeanName(String)}
	 */
	@Override
	public void setBeanName(String name) {
		System.out.println("BeanNameAware.setBeanName");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactoryAware.setBeanFactory");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("ApplicationContextAware.setApplicationContext");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBean.afterPropertiesSet");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("DisposableBean.destroy");
	}

	// 自定义初始化方法
	@PostConstruct
	public void springPostConstruct(){
		System.out.println("@PostConstruct");
	}

	// 自定义销毁方法
	@PreDestroy
	public void springPreDestroy(){
		System.out.println("@PreDestroy");
	}

}
