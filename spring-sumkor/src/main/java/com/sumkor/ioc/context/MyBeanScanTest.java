package com.sumkor.ioc.context;

import com.sumkor.ioc.bean.scan.MyBeanWithAnnotation;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * 2. bean注解扫描
 * <p>
 * 解析配置类下的@Bean
 * 配置类指的是@Component注解的类，@Configuration实际上也是@Component
 *
 * @author Sumkor
 * @since 2020/6/15
 */
public class MyBeanScanTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		/**
		 * 父构造方法中，创建 beanFactory
		 * @see GenericApplicationContext#GenericApplicationContext()
		 *
		 * 默认扫描 @Component 注解！！
		 * @see ClassPathBeanDefinitionScanner#ClassPathBeanDefinitionScanner(org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 * @see ClassPathScanningCandidateComponentProvider#registerDefaultFilters()
		 */

		context.scan("com.sumkor.ioc.bean.scan");
		/**
		 * 扫描 @Component 注解的类，并注册，入口
		 * @see ClassPathBeanDefinitionScanner#doScan(java.lang.String...)
		 * 扫描包路径下的文件，解析为 Resource 对象，判断是否存在 @Component 注解的类，若存在则注册到 beanFactory. (这里注册了 myBeanWithAnnotation，为 ScannedGenericBeanDefinition 实例)
		 * @see ClassPathScanningCandidateComponentProvider#scanCandidateComponents(java.lang.String)
		 * @see ClassPathScanningCandidateComponentProvider#isCandidateComponent(org.springframework.core.type.classreading.MetadataReader)
		 */

		context.refresh();
		/**
		 * 1. 执行 BeanFactoryPostProcessor: 将 @Component 注解的类里面的 @Bean 方法，注册到 beanFactory，入口
		 * @see AbstractApplicationContext#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
		 * @see org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.List)
		 * @see org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanDefinitionRegistryPostProcessors(java.util.Collection, org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 *
		 * 1.2 执行 ConfigurationClassPostProcessor: 从 beanFactory 中获取 @Component 等修饰的配置类，解析其中的 @Bean 注解，解析为 beanDefinition 进行注册
		 * @see ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 * @see ConfigurationClassPostProcessor#processConfigBeanDefinitions(org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 *
		 * 1.2.1 校验 beanDefinition 对应的类是否使用了 @Configuration、@Component、@Import 等注解 {@link org.springframework.context.annotation.ConfigurationClassUtils#candidateIndicators}
		 * @see ConfigurationClassUtils#checkConfigurationClassCandidate(org.springframework.beans.factory.config.BeanDefinition, org.springframework.core.type.classreading.MetadataReaderFactory)
		 *
		 * 1.2.2 对符合上一步条件的 beanDefinition 进行解析，将 @Bean 注解的方法解析为 MethodMetadata 实例
		 * @see ConfigurationClassParser#parse(java.util.Set)
		 * @see ConfigurationClassParser#doProcessConfigurationClass(org.springframework.context.annotation.ConfigurationClass, org.springframework.context.annotation.ConfigurationClassParser.SourceClass, java.util.function.Predicate)
		 * @see ConfigurationClassParser#retrieveBeanMethodMetadata(org.springframework.context.annotation.ConfigurationClassParser.SourceClass)
		 *
		 * 1.2.3 将 MethodMetadata 实例解析为 beanDefinition，即 ConfigurationClassBeanDefinitionReader$ConfigurationClassBeanDefinition 实例，并进行注册
		 * @see ConfigurationClassBeanDefinitionReader#loadBeanDefinitions(java.util.Set)
		 * @see ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForConfigurationClass(org.springframework.context.annotation.ConfigurationClass, org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.TrackedConditionEvaluator)
		 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod(org.springframework.context.annotation.BeanMethod)
		 */

		MyBeanWithAnnotation myBean = (MyBeanWithAnnotation) context.getBean("myBeanWithAnnotation");
		myBean.sayHello();

		String aBean = (String) context.getBean("aBean");
		System.out.println(aBean);
	}
}
