package com.sumkor.ioc.factorybean.mybatis.process;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * OrderMapper -> new MyFactoryBean(OrderMapper.class) -> OrderMapper代理对象
 * UserMapper -> new MyFactoryBean(UserMapper.class) -> UserMapper代理对象
 * <p>
 * 此时，需要根据MyFactoryBean.class生成两个beanDefinition，它们的beanName不同，beanClass相同
 *
 * @author Sumkor
 * @since 2020/9/8
 */
public class MyFactoryBean implements FactoryBean<Object> {

	private final Class<?> mapper;

	private MyFactoryBean(Class<?> mapper) {
		this.mapper = mapper;
	}

	@Override
	public Object getObject() throws Exception {

		return Proxy.newProxyInstance(MyFactoryBean.class.getClassLoader(), new Class<?>[]{mapper}, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return null;
			}
		});
	}

	@Override
	public Class<?> getObjectType() {
		return mapper;
	}
}
