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
import java.util.Map;

/**
 * 将 BeanDefinition 注册到 BeanFactory.BeanDefinitionMap
 *
 * @author Sumkor
 * @since 2020/9/14
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {

		// 可以改为扫描包路径，获取mappers
		Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(MyScan.class.getName());
		Object value = annotationAttributes.get("value");
		System.out.println("value = " + value); // com.sumkor.ioc.factorybean.mybatis.mapper

		List<Class<?>> mappers = new ArrayList<>();
		mappers.add(UserMapper.class);
		mappers.add(OrderMapper.class);

		for (Class<?> mapper : mappers) {
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
			AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
			beanDefinition.setBeanClass(MyFactoryBean.class);
			beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapper);// MyFactoryBean构造函数入参赋值
			registry.registerBeanDefinition(mapper.getSimpleName(), beanDefinition);// 注册BeanDefinition
		}
	}
}
