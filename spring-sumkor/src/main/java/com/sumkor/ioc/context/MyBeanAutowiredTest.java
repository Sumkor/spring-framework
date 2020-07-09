package com.sumkor.ioc.context;

import com.sumkor.ioc.bean.circle.MyBeanA;
import com.sumkor.ioc.bean.circle.MyBeanB;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * 3. 自动注入 循环依赖
 *
 * @author Sumkor
 * @since 2020/6/16
 */
public class MyBeanAutowiredTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.sumkor.ioc.bean.circle");
		/**
		 * 扫描之后，分别注册 myBeanA 和 myBeanB 两个 beanDefinition
		 * @see ClassPathBeanDefinitionScanner#doScan(java.lang.String...)
		 */

		context.refresh();
		/**
		 * 1. 创建 bean，解决循环依赖
		 * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 *
		 * 1.1 查找 MyBeanA 的类中依赖的其他 bean
		 * @see AbstractAutowireCapableBeanFactory#applyMergedBeanDefinitionPostProcessors(org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Class, java.lang.String)
		 * @see AutowiredAnnotationBeanPostProcessor#postProcessMergedBeanDefinition(org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Class, java.lang.String)
		 * 将 MyBeanA 所依赖的 bean 封装成 InjectionMetadata
		 * @see AutowiredAnnotationBeanPostProcessor#findAutowiringMetadata(java.lang.String, java.lang.Class, org.springframework.beans.PropertyValues)|
		 * 将 MyBeanA 中的成员变量 field MyBeanB，封装成 AutowiredFieldElement 对象，再封装到 InjectionMetadata 对象返回
		 * @see AutowiredAnnotationBeanPostProcessor#buildAutowiringMetadata(java.lang.Class)
		 * 最后将结果存在 {@link AutowiredAnnotationBeanPostProcessor#injectionMetadataCache} 之中：key 为 MyBeanA，value 为 InjectionMetadata 对象
		 *
		 * 1.2 提前暴露 MyBeanA 引用，存储在 {@link DefaultSingletonBeanRegistry#singletonFactories} 和 {@link DefaultSingletonBeanRegistry#registeredSingletons}
		 * @see DefaultSingletonBeanRegistry#addSingletonFactory(java.lang.String, org.springframework.beans.factory.ObjectFactory)
		 *
		 * 1.3 设置 MyBeanA 的属性
		 * @see AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, org.springframework.beans.BeanWrapper)
		 *
		 * 从缓存 {@link AutowiredAnnotationBeanPostProcessor#injectionMetadataCache} 中取得 InjectionMetadata 对象，为 MyBeanA 的属性注入 MyBeanB
		 * @see AutowiredAnnotationBeanPostProcessor#postProcessProperties(org.springframework.beans.PropertyValues, java.lang.Object, java.lang.String)
		 * @see InjectionMetadata#inject(java.lang.Object, java.lang.String, org.springframework.beans.PropertyValues)
		 * @see AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(java.lang.Object, java.lang.String, org.springframework.beans.PropertyValues)
		 *
		 * 执行依赖注入
		 * @see DefaultListableBeanFactory#resolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set, org.springframework.beans.TypeConverter)
		 * @see DefaultListableBeanFactory#doResolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set, org.springframework.beans.TypeConverter)
		 *
		 * 1.3.1 找到可以注入的 bean：MyBeanB，但是 MyBeanB 未实例化，需要先创建
		 * @see DefaultListableBeanFactory#findAutowireCandidates(java.lang.String, java.lang.Class, org.springframework.beans.factory.config.DependencyDescriptor)
		 *
		 * 1.3.2 创建 MyBeanB（整体流程与创建 MyBeanA 一致）
		 * @see DependencyDescriptor#resolveCandidate(java.lang.String, java.lang.Class, org.springframework.beans.factory.BeanFactory)
		 * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 * 将 MyBeanB 存储在 {@link AutowiredAnnotationBeanPostProcessor#injectionMetadataCache} 之中
		 * 设置 MyBeanB 的属性，找到可供注入的 bean：MyBeanA，这里可以直接从 BeanFactory 中取得 MyBeanA 实例
		 * @see AutowiredAnnotationBeanPostProcessor#postProcessProperties(org.springframework.beans.PropertyValues, java.lang.Object, java.lang.String)
		 * @see DefaultListableBeanFactory#doResolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set, org.springframework.beans.TypeConverter)
		 * 最后，执行属性赋值
		 * @see AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(java.lang.Object, java.lang.String, org.springframework.beans.PropertyValues)
		 * 注意，对 MyBeanB 循环依赖 earlySingletonExposure 二次处理，是空处理
		 *
		 * 1.3.3 完成实例化 MyBeanB 之后，为 MyBeanA 的属性赋值
		 * @see AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(java.lang.Object, java.lang.String, org.springframework.beans.PropertyValues)
		 *
		 * 1.4 二次处理循环依赖，检查 MyBeanA 在设置完属性之后，是否发生改变
		 * @see AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
		 */

		MyBeanA myBeanA = (MyBeanA) context.getBean("myBeanA");
		myBeanA.sayHello();
		myBeanA.sayHelloAgain();

		MyBeanB myBeanB = (MyBeanB) context.getBean("myBeanB");
		myBeanB.sayHello();
		myBeanB.sayHelloAgain();

		/**
		 * 执行结果：
		 *
		 * hello MyBeanA
		 * hello MyBeanB
		 * hello MyBeanB
		 * hello MyBeanA
		 */
	}
}
