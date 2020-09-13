package com.sumkor.ioc.factorybean.mybatis;

import com.sumkor.ioc.factorybean.mybatis.mapper.MyMapper;
import com.sumkor.ioc.factorybean.mybatis.mapper.OrderMapper;
import com.sumkor.ioc.factorybean.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Sumkor
 * @since 2020/9/8
 */
@Component
public class MyFactoryBean implements FactoryBean<Object> {

//	private final Class<MyMapper>[] mapper;
//
//	private MyFactoryBean(Class<MyMapper>[] mapper) {
//		this.mapper = mapper;
//	}

	@Override
	public Object getObject() throws Exception {

		Object mapper = Proxy.newProxyInstance(MyFactoryBean.class.getClassLoader(), new Class<?>[]{UserMapper.class}, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return null;
			}
		});
		return mapper;
	}

	@Override
	public Class<?> getObjectType() {
		return UserMapper.class;
	}
}
