package com.sumkor.aop.context;

import com.sumkor.aop.config.AopConfig;
import com.sumkor.aop.service.MyService;
import com.sumkor.aop.service.MyServiceImpl;
import com.sumkor.aop.service.MyServiceNoFace;
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
		 * 1.2 EnableAspectJAutoProxy开启
		 *
		 *
		 */

		System.out.println("------------------------");

		MyService myService = context.getBean(MyService.class);
		myService.say();
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
