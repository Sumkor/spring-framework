package com.sumkor.ioc.context;

import com.sumkor.ioc.bean.MyBeanLife;
import com.sumkor.ioc.bean.MyBeanPostProcessor;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * bean生命周期
 * https://www.cnblogs.com/javazhiyin/p/10905294.html
 *
 * @author Sumkor
 * @since 2020/5/14
 */
public class MyBeanLifeTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		/**
		 * 父类构造方法 创建 beanFactory
		 * @see GenericApplicationContext#GenericApplicationContext()
		 */

		context.register(MyBeanLife.class);
		/**
		 * 注册 BeanDefinition 至 beanFactory
		 * @see AnnotatedBeanDefinitionReader#doRegisterBean(java.lang.Class, java.lang.String, java.lang.Class[], java.util.function.Supplier, org.springframework.beans.factory.config.BeanDefinitionCustomizer[])
		 * @see BeanDefinitionReaderUtils#registerBeanDefinition(org.springframework.beans.factory.config.BeanDefinitionHolder, org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 */

		context.getBeanFactory().addBeanPostProcessor(new MyBeanPostProcessor());
		/**
		 * 添加自定的 BeanPostProcessor
		 */

		context.refresh();
		/**
		 * 1. 入口 创建非工厂 bean
		 * @see DefaultListableBeanFactory#preInstantiateSingletons()
		 * @see AbstractBeanFactory#getBean(java.lang.String)
		 *
		 * 2. 创建单例 bean
		 * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
		 * 处理 BeanPostProcessors 代理
		 * @see AbstractAutowireCapableBeanFactory#createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 *
		 * 3. 实例化 bean
		 * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 *
		 * 3.1 调用 bean 构造方法进行实例化
		 * @see AbstractAutowireCapableBeanFactory#createBeanInstance(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 * @see AbstractAutowireCapableBeanFactory#instantiateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition)
		 * 调用 bean 的无参构造方法
		 * @see SimpleInstantiationStrategy#instantiate(org.springframework.beans.factory.support.RootBeanDefinition, java.lang.String, org.springframework.beans.factory.BeanFactory)
		 *
		 * 3.2 执行 aware/InitializingBean 方法入口
		 * @see AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
		 *
		 * 3.2.1 执行 BeanNameAware/BeanFactoryAware 方法
		 * @see AbstractAutowireCapableBeanFactory#invokeAwareMethods(java.lang.String, java.lang.Object)
		 *
		 * 3.2.2 执行 BeanPostProcessor.applyBeanPostProcessorsBeforeInitialization 方法
		 * @see AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)
		 *
		 * 3.2.2.0 执行自定的 BeanPostProcessor 方法
		 * 3.2.2.1 执行 ApplicationContextAware 方法
		 * @see org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces(java.lang.Object)
		 * 3.2.2.2 执行 PostConstruct 注解方法
		 * @see InitDestroyAnnotationBeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
		 *
		 * 3.2.3 执行 InitializingBean 方法，其中先执行 afterPropertiesSet，再执行 initMethod
		 * @see AbstractAutowireCapableBeanFactory#invokeInitMethods(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
		 *
		 * 3.2.4 执行 BeanPostProcessor.applyBeanPostProcessorsAfterInitialization 方法
		 * @see AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsAfterInitialization(java.lang.Object, java.lang.String)
		 *
		 * 4. 将 bean 实例存储到 BeanFactory
		 * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
		 * @see DefaultSingletonBeanRegistry#getSingleton(java.lang.String, org.springframework.beans.factory.ObjectFactory)
		 * @see DefaultSingletonBeanRegistry#addSingleton(java.lang.String, java.lang.Object)
		 */

		MyBeanLife myBeanLife = (MyBeanLife) context.getBean("myBeanLife");
		myBeanLife.sayHello();

		context.close();
		/**
		 * @see AbstractApplicationContext#doClose()
		 * @see AbstractApplicationContext#destroyBeans()
		 * @see DisposableBeanAdapter#destroy()
		 * 其中，先执行 PreDestroy 注解方法，再执行 DisposableBean 方法
		 */

		/**
		 * 执行结果：
		 *
		 * MyBeanLife.Constructor
		 *
		 * BeanNameAware.setBeanName
		 * BeanFactoryAware.setBeanFactory
		 *
		 * MyBeanPostProcessor.postProcessBeforeInitialization
		 * ApplicationContextAware.setApplicationContext
		 * @PostConstruct
		 *
		 * InitializingBean.afterPropertiesSet
		 *
		 * MyBeanPostProcessor.postProcessAfterInitialization
		 *
		 * MyBeanLife.sayHello:哇哦
		 *
		 * @PreDestroy
		 * DisposableBean.destroy
		 */
	}
}
