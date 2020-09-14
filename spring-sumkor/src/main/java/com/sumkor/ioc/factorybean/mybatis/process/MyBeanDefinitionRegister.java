package com.sumkor.ioc.factorybean.mybatis.process;

import com.sumkor.ioc.factorybean.mybatis.mapper.OrderMapper;
import com.sumkor.ioc.factorybean.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 将 BeanDefinition 注册到 BeanFactory 之中的 BeanDefinitionMap 的方式：
 * 1. ComponentScan 扫描，生成 BeanDefinition 并注册
 * 2. Import 导入，生成 BeanDefinition 并注册
 *
 * @author Sumkor
 * @since 2020/9/14
 */
public class MyBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {

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
}
