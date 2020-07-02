package com.sumkor.ioc.factory;

import com.sumkor.ioc.bean.MyBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.*;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * 从 xml 文件中获取 bean
 *
 * @author Sumkor
 * @since 2020/5/14
 */
public class MyBeanXmlTest {

	public static void main(String[] args) {
//		BeanFactory bf = new XmlBeanFactory(new ClassPathResource("MyBean.xml"));
//		MyBean myBean = (MyBean) bf.getBean("myBean");
		BeanFactory bf = new ClassPathXmlApplicationContext("MyBean.xml");

		MyBean myBean = (MyBean) bf.getBean("myBean");
		myBean.sayHello();

		/**
		 * 入口
		 * @see ClassPathXmlApplicationContext#ClassPathXmlApplicationContext(java.lang.String[], boolean, org.springframework.context.ApplicationContext)
		 *
		 * 将入参"MyBean.xml"经过以下解析，得到的还是"MyBean.xml"，意义不明
		 * @see AbstractRefreshableConfigApplicationContext#setConfigLocations(java.lang.String...)
		 * @see PropertyPlaceholderHelper#parseStringValue(java.lang.String, org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver, java.util.Set)
		 *
		 * 执行，关键位置
		 * @see AbstractApplicationContext#refresh()
		 *
		 * 其中：
		 *
		 * 1. 刷新 beanFactory，设置属性 {@link AbstractRefreshableApplicationContext#beanFactory}，并获取 beanFactory
		 * @see AbstractApplicationContext#obtainFreshBeanFactory()
		 * @see AbstractRefreshableApplicationContext#refreshBeanFactory()
		 *
		 * 1.1 创建 beanFactory，即创建对象 {@link DefaultListableBeanFactory}，且 ParentBeanFactory 为 null
		 * @see AbstractRefreshableApplicationContext#createBeanFactory()
		 *
		 * 1.2 从 XmlBeanDefinitionReader 加载 beanDefinition(在 java 中 class 产生对象；在 spring 中 BeanDefinition 产生 bean)，并注册
		 * @see AbstractXmlApplicationContext#loadBeanDefinitions(org.springframework.beans.factory.support.DefaultListableBeanFactory)
		 * @see AbstractXmlApplicationContext#loadBeanDefinitions(org.springframework.beans.factory.xml.XmlBeanDefinitionReader)
		 *
		 * 1.2.1 利用类加载器读取 MyBean.xml 文件转换为二进制字节流
		 * @see AbstractBeanDefinitionReader#loadBeanDefinitions(java.lang.String, java.util.Set)
		 * @see XmlBeanDefinitionReader#loadBeanDefinitions(org.springframework.core.io.support.EncodedResource)
		 *
		 * 1.2.2 将 xml 文件的二进制流转换为 Document 对象
		 * @see XmlBeanDefinitionReader#doLoadBeanDefinitions(org.xml.sax.InputSource, org.springframework.core.io.Resource)
		 *
		 * 1.2.3 将 xml 文件的 Document 对象，转换为 BeanDefinition 对象，并注册
		 * @see DefaultBeanDefinitionDocumentReader#doRegisterBeanDefinitions(org.w3c.dom.Element)
		 * @see DefaultBeanDefinitionDocumentReader#processBeanDefinition(org.w3c.dom.Element, org.springframework.beans.factory.xml.BeanDefinitionParserDelegate)
		 *
		 * 1.2.3.1 创建 BeanDefinition 对象，即 {@link GenericBeanDefinition} 的实例
		 * @see BeanDefinitionParserDelegate#parseBeanDefinitionElement(org.w3c.dom.Element, org.springframework.beans.factory.config.BeanDefinition)
		 * @see BeanDefinitionReaderUtils#createBeanDefinition(java.lang.String, java.lang.String, java.lang.ClassLoader)
		 *
		 * 1.2.3.2 注册 BeanDefinition 对象至 {@link DefaultListableBeanFactory#beanDefinitionMap} 之中
		 * @see BeanDefinitionReaderUtils#registerBeanDefinition(org.springframework.beans.factory.config.BeanDefinitionHolder, org.springframework.beans.factory.support.BeanDefinitionRegistry)
		 * @see DefaultListableBeanFactory#registerBeanDefinition(java.lang.String, org.springframework.beans.factory.config.BeanDefinition)
		 *
		 *
		 *
		 * 10. 对非延迟加载的单例进行实例化
		 * @see AbstractApplicationContext#finishBeanFactoryInitialization(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
		 * @see DefaultListableBeanFactory#preInstantiateSingletons()
		 *
		 * 10.1 一步步超实例化操作走。如果已经实例化，从缓存中获取实例对象
		 * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
		 * @see AbstractBeanFactory#createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 * @see AbstractAutowireCapableBeanFactory#createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 *
		 * 10.2 实例化 bean 对象
		 * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 *
		 * 10.2.1 执行 bean 对象的构造方法，将结果封装为 {@link BeanWrapper} 对象
		 * @see AbstractAutowireCapableBeanFactory#instantiateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition)
		 * @see SimpleInstantiationStrategy#instantiate(org.springframework.beans.factory.support.RootBeanDefinition, java.lang.String, org.springframework.beans.factory.BeanFactory)
		 * @see BeanUtils#instantiateClass(java.lang.reflect.Constructor, java.lang.Object...)
		 *
		 * 10.2.2 处理循环依赖 org/springframework/beans/factory/support/AbstractAutowireCapableBeanFactory.java:581
		 *
		 * 10.2.3 初始化 bean，执行其中的 autowire/init 方法
		 * @see AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
		 */
	}
}
