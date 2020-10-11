package com.sumkor.ioc.factorybean.mybatis.process;

import com.sumkor.ioc.factorybean.mybatis.mapper.OrderMapper;
import com.sumkor.ioc.factorybean.mybatis.mapper.UserMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用 BeanFactoryPostProcessor 向 BeanFactory 注册 BeanDefinition
 *
 * @author Sumkor
 * @since 2020/9/18
 */
//@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

	/**
	 * @param registry the bean definition registry used by the application context
	 * @throws BeansException
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		List<Class<?>> mappers = new ArrayList<>();
		mappers.add(UserMapper.class);
		mappers.add(OrderMapper.class);

		for (Class<?> mapper : mappers) {
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
			AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
			beanDefinition.setBeanClass(MyFactoryBean.class);
			beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapper);
			registry.registerBeanDefinition(mapper.getSimpleName(), beanDefinition);
		}
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}
}
