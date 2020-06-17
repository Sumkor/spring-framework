package com.sumkor.ioc.factory;

import com.sumkor.ioc.bean.MyBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.SimpleInstantiationStrategy;

import java.lang.reflect.Constructor;

/**
 * @author Sumkor
 * @since 2020/6/4
 */
public class MyBeanTest {

	@Test
	public void test() {
		System.out.println("hello 中文");
	}

	/**
	 * 利用构造方法创建 bean 实例
	 * @see SimpleInstantiationStrategy#instantiate(org.springframework.beans.factory.support.RootBeanDefinition, java.lang.String, org.springframework.beans.factory.BeanFactory)
	 */
	@Test
	public void createBean() throws ClassNotFoundException, NoSuchMethodException {
		Class<?> clazz = Class.forName("com.sumkor.ioc.bean.MyBean");
		Constructor<?> constructorToUse = clazz.getDeclaredConstructor();
		Object object = BeanUtils.instantiateClass(constructorToUse);
		MyBean myBean = (MyBean) object;
		myBean.sayHello();
	}

}
