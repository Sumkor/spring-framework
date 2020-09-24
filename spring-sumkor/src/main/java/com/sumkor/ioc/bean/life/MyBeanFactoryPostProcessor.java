package com.sumkor.ioc.bean.life;

import com.sumkor.ioc.factorybean.mybatis.process.MyImportBeanDefinitionRegistrar;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 当 Spring 将 BeanFactory 组建完成之后，使用后置处理器去操作 BeanFactory
 * BeanFactory 组建完成的标识，是 Spring 扫描完包路径之后，将扫描的的对象解析成 BeanDefinition，并放置到了 BeanFactory 之中的 beanDefinitionMap
 * <p>
 * 另外，这里只能从 beanDefinitionMap 之中取 BeanDefinition，不能往里面注册
 * 如何注册自定的 BeanDefinition，见 {@link MyImportBeanDefinitionRegistrar}
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
