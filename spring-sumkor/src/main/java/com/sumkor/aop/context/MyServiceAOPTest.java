package com.sumkor.aop.context;

import com.sumkor.aop.config.AopConfig;
import com.sumkor.aop.service.MyService;
import com.sumkor.aop.service.MyServiceImpl;
import com.sumkor.aop.service.MyServiceNoFace;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author Sumkor
 * @since 2020/7/16
 */
public class MyServiceAOPTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
		/**
		 * 总入口
		 * @see AbstractApplicationContext#refresh()
		 *
		 * 1. 执行 BeanFactoryPostProcessor，解析AppConfig.class
		 * @see AbstractApplicationContext#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
		 *
		 * 1.1 ComponentScan包扫描
		 * @see ConfigurationClassParser#doProcessConfigurationClass(org.springframework.context.annotation.ConfigurationClass, org.springframework.context.annotation.ConfigurationClassParser.SourceClass, java.util.function.Predicate)
		 * @see ComponentScanAnnotationParser#parse(org.springframework.core.annotation.AnnotationAttributes, java.lang.String)
		 * @see ClassPathBeanDefinitionScanner#doScan(java.lang.String...)
		 * @see ClassPathScanningCandidateComponentProvider#scanCandidateComponents(java.lang.String)
		 *
		 * 1.2 EnableAspectJAutoProxy开启，注册BeanPostProcessor:{@link AnnotationAwareAspectJAutoProxyCreator}
		 * @see AspectJAutoProxyRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 * @see AopConfigUtils#registerAspectJAnnotationAutoProxyCreatorIfNecessary(org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 * @see AopConfigUtils#registerAspectJAnnotationAutoProxyCreatorIfNecessary(org.springframework.beans.factory.support.BeanDefinitionRegistry, java.lang.Object)
		 *
		 * 2. 对原始bean进行代理
		 * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 * @see AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
		 *
		 * 2.1 BeanPostProcessor.postProcessAfterInitialization之中进行代理，即执行{@link AnnotationAwareAspectJAutoProxyCreator}
		 * @see AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsAfterInitialization(java.lang.Object, java.lang.String)
		 * @see AbstractAutoProxyCreator#postProcessAfterInitialization(java.lang.Object, java.lang.String)
		 * @see AbstractAutoProxyCreator#wrapIfNecessary(java.lang.Object, java.lang.String, java.lang.Object)
		 * @see AbstractAutoProxyCreator#createProxy(java.lang.Class, java.lang.String, java.lang.Object[], org.springframework.aop.TargetSource)
		 * @see ProxyFactory#getProxy(java.lang.ClassLoader)
		 *
		 * A. JDK动态代理
		 * @see org.springframework.aop.framework.JdkDynamicAopProxy#getProxy(java.lang.ClassLoader)
		 * B. CGLIB动态代理
		 * @see org.springframework.aop.framework.CglibAopProxy#getProxy(java.lang.ClassLoader)
		 */

		System.out.println("------------------------");

		MyService myService = context.getBean(MyService.class);
		/**
		 * context.getBean方法传入的是类型，则调用
		 * @see AbstractApplicationContext#getBean(java.lang.Class)
		 * @see DefaultListableBeanFactory#getBean(java.lang.Class, java.lang.Object...)
		 * @see DefaultListableBeanFactory#resolveNamedBean(org.springframework.core.ResolvableType, java.lang.Object[], boolean)
		 * 最终也是调用到了
		 * @see AbstractBeanFactory#getBean(java.lang.String, java.lang.Class, java.lang.Object...)
		 * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
		 */
		myService.say();
		myService.play();
		System.out.println(myService instanceof MyServiceImpl);
		/**
		 * 使用jdk动态代理时，返回false
		 * 使用cglib动态代理时，返回true
		 *
		 * jdk动态代理：bean extends Prosy implement MyService
		 * cglib动态代理：bean extends MyServiceImpl implement MyService
		 */

		System.out.println("------------------------");

		MyServiceNoFace myServiceNoFace = context.getBean(MyServiceNoFace.class);
		myServiceNoFace.say();
	}
}
