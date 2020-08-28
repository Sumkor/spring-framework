package com.sumkor.ioc.context;

import com.sumkor.ioc.bean.life.MyBeanFactoryPostProcessor;
import com.sumkor.ioc.bean.life.MyBeanLife;
import com.sumkor.ioc.bean.life.MyBeanPostProcessor;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * 1. bean生命周期
 * https://www.cnblogs.com/javazhiyin/p/10905294.html
 *
 * class -> BeanDefinition -> BeanFactoryPostProcessor -> new Object -> 填充属性 -> Aware -> BeanPostProcessor/AOP -> 单例池
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

		context.addBeanFactoryPostProcessor(new MyBeanFactoryPostProcessor());
		context.getBeanFactory().addBeanPostProcessor(new MyBeanPostProcessor());
		/**
		 * 添加自定的 BeanFactoryPostProcessor 和 BeanPostProcessor
		 */

		context.refresh();
		/**
		 * 1. BeanFactoryPostProcessor 实例化并执行。这里只有 internalConfigurationAnnotationProcessor 等内部处理器
		 * @see AbstractApplicationContext#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
		 * @see org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.List)
		 *
		 * 2. BeanPostProcessor 实例化并注册。这里只有 internalAutowiredAnnotationProcessor internalCommonAnnotationProcessor 等内部处理器
		 * @see AbstractApplicationContext#registerBeanPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
		 *
		 * 3. bean 的实例化入口
		 * @see AbstractApplicationContext#finishBeanFactoryInitialization(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
		 * @see DefaultListableBeanFactory#preInstantiateSingletons()
		 *
		 * 3.1 若从 getSingleton 取不到 MyBeanLife bean，则进行构造 RootBeanDefinition 并创建单例 bean
		 * @see AbstractBeanFactory#getBean(java.lang.String)
		 * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
		 *
		 * 3.1.1 创建 MyBeanLife bean 单例
		 * @see AbstractAutowireCapableBeanFactory#createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 *
		 * 3.1.1.1 调用 bean 构造方法进行实例化，返回 BeanWrapper
		 * @see AbstractAutowireCapableBeanFactory#createBeanInstance(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 * @see AbstractAutowireCapableBeanFactory#instantiateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition)
		 * 调用 bean 的无参构造方法
		 * @see SimpleInstantiationStrategy#instantiate(org.springframework.beans.factory.support.RootBeanDefinition, java.lang.String, org.springframework.beans.factory.BeanFactory)
		 *
		 * 3.1.1.2 设置 bean 的属性
		 * @see AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, org.springframework.beans.BeanWrapper)
		 *
		 * 3.1.1.3 调用 bean 的其他初始化方法（继承了 BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean 等情况下）
		 * @see AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
		 *
		 * A. 执行 BeanNameAware/BeanFactoryAware 方法
		 * @see AbstractAutowireCapableBeanFactory#invokeAwareMethods(java.lang.String, java.lang.Object)
		 *
		 * B. 执行 BeanPostProcessor.applyBeanPostProcessorsBeforeInitialization 方法
		 * @see AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)
		 *
		 * B.1 执行自定的 BeanPostProcessor 方法
		 * B.2 执行 ApplicationContextAware 方法
		 * @see org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces(java.lang.Object)
		 * B.3 执行 PostConstruct 注解方法
		 * @see InitDestroyAnnotationBeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
		 *
		 * C. 执行 InitializingBean 类的方法，其中先执行 afterPropertiesSet，再执行 initMethod(xml文件中配置了init-method)
		 * @see AbstractAutowireCapableBeanFactory#invokeInitMethods(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
		 *
		 * D. 执行 BeanPostProcessor.applyBeanPostProcessorsAfterInitialization 方法
		 * @see AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsAfterInitialization(java.lang.Object, java.lang.String)
		 *
		 * 3.1.2 将 bean 实例存储到 BeanFactory
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
		 * MyBeanFactoryPostProcessor.postProcessBeanFactory
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
