package com.sumkor.ioc.context;

import com.sumkor.ioc.bean.circle.MyBeanA;
import com.sumkor.ioc.bean.circle.MyBeanB;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * 3. 自动注入 循环依赖
 *
 * 执行BeanA的构造方法 -> 曝光BeanA引用 ->
 * 设置BeanA的属性(BeanB)开始 -> 执行BeanB的构造方法 -> 曝光BeanB引用 -> 设置BeanB的属性(BeanA)成功 -> 完成创建BeanB->
 * 设置BeanA的属性(BeanB)结束 -> 完成创建BeanA
 *
 * 扩展阅读：
 * Spring IoC 依赖注入（三）resolveDependency
 * https://www.cnblogs.com/binarylei/p/12337145.html
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
		 * 1.2 提前暴露 MyBeanA，存储在第三级缓存 {@link DefaultSingletonBeanRegistry#singletonFactories} 和 {@link DefaultSingletonBeanRegistry#registeredSingletons}
		 * @see DefaultSingletonBeanRegistry#addSingletonFactory(java.lang.String, org.springframework.beans.factory.ObjectFactory)
		 *
		 * 1.3 设置 MyBeanA 的属性
		 * @see AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, org.springframework.beans.BeanWrapper)
		 * @see AutowiredAnnotationBeanPostProcessor#postProcessProperties(org.springframework.beans.PropertyValues, java.lang.Object, java.lang.String)
		 *
		 *
		 * 1.3.1 从 injectionMetadataCache 之中取的 InjectionMetadata 对象，表明 MyBeanA 需要注入 MyBeanB
		 * @see AutowiredAnnotationBeanPostProcessor#findAutowiringMetadata(java.lang.String, java.lang.Class, org.springframework.beans.PropertyValues)
		 *
		 * 1.3.2 对 InjectionMetadata 对象执行注入操作：得到所需要注入的 MyBeanB 对象实例，将该实例注入 MyBeanA 的成员变量中
		 * @see InjectionMetadata#inject(java.lang.Object, java.lang.String, org.springframework.beans.PropertyValues)
		 * @see AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(java.lang.Object, java.lang.String, org.springframework.beans.PropertyValues)
		 *
		 * 1.3.2.1 得到所需要注入的 MyBeanB 对象实例
		 * @see DefaultListableBeanFactory#resolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set, org.springframework.beans.TypeConverter)
		 * @see DefaultListableBeanFactory#doResolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set, org.springframework.beans.TypeConverter)
		 *
		 * A. 【入参】根据 beanName:'myBeanA' 所依赖的 MyBeanB.class 类型，【出参】找到候选 map，其 key 为 beanName:'myBeanB'，其 value 为 beanType:MyBeanB.class
		 * @see DefaultListableBeanFactory#findAutowireCandidates(java.lang.String, java.lang.Class, org.springframework.beans.factory.config.DependencyDescriptor)
		 *
		 * 其中，构造候选 map，需要根据 beanName 获取 beanType
		 * @see DefaultListableBeanFactory#addCandidateEntry(java.util.Map, java.lang.String, org.springframework.beans.factory.config.DependencyDescriptor, java.lang.Class)
		 * 首先从单例池中获取 MyBeanB 实例，但是获取不到，转而从 BeanFactory 中获取 BeanDefinition，依旧获取不到
		 * @see AbstractBeanFactory#getType(java.lang.String)
		 * 通过预测大法，从 RootBeanDefinition 拿到了 beanType:MyBeanB.class
		 * @see AbstractAutowireCapableBeanFactory#predictBeanType(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Class[])
		 *
		 * B. 拿到了候选 Map 之后，从多个候选 beanName 中，找到符合的 beanName
		 * @see DefaultListableBeanFactory#determineAutowireCandidate(java.util.Map, org.springframework.beans.factory.config.DependencyDescriptor)
		 *
		 * C. 根据 beanName 获取 bean 的实例
		 * @see DependencyDescriptor#resolveCandidate(java.lang.String, java.lang.Class, org.springframework.beans.factory.BeanFactory)
		 *
		 * C.1 根据 beanName'myBeanB' 获取 MyBeanB 实例，实际是获取不到的，需要进行创建（整体流程与创建 MyBeanA 一致）
		 * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
		 *
		 * C.2 这里只需要关注 MyBeanB 的创建过程中，如何注入 MyBeanA
		 * @see AbstractAutowireCapableBeanFactory#populateBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, org.springframework.beans.BeanWrapper)
		 * @see AutowiredAnnotationBeanPostProcessor#postProcessProperties(org.springframework.beans.PropertyValues, java.lang.Object, java.lang.String)
		 * @see AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(java.lang.Object, java.lang.String, org.springframework.beans.PropertyValues)
		 * @see DefaultListableBeanFactory#doResolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set, org.springframework.beans.TypeConverter)
		 *
		 * 这里获取集合 Map<key:'myBeanA',value:MyBeanA.class>
		 * 在单例池中获取不到 MyBeanA 实例，因为用的是 getSingleton(beanName, false) 只从第一、二级缓存获取！！！
		 * 只能同样根据预测大法，由 beanName 获取 beanType
		 * @see AbstractBeanFactory#getType(java.lang.String, boolean)
		 *
		 * 尝试根据 beanName、beanType 来获取 bean 实例
		 * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
		 * 在单例池中获取到了 MyBeanA 实例，因为用的是 getSingleton(beanName, true) 从第三级缓存获取到了！！！
		 * @see DefaultSingletonBeanRegistry#getSingleton(java.lang.String, boolean)
		 *
		 * 执行 MyBeanA 提前曝光的 lambda，得到 myBeanA 实例，此时该实例的属性是空的
		 * @see AbstractAutowireCapableBeanFactory#getEarlyBeanReference(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object)
		 *
		 * 最后将 MyBeanA 注入 MyBeanB 的属性之中，并将 MyBeanB 放入单例池
		 *
		 * D. 返回得到的 MyBeanB 实例
		 *
		 * 1.3.2.2 将 MyBeanB 实例注入 MyBeanA 的属性之中
		 * field.set(bean, value);
		 *
		 *
		 * 1.4 初始化 MyBeanA
		 * @see AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)
		 * 关注这里对 Autowired 等相关 BeanPostProcessor 的执行逻辑，其实都是空操作
		 * CommonAnnotationBeanPostProcessor -> {@link InitDestroyAnnotationBeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)}
		 * AutowiredAnnotationBeanPostProcessor -> {@link InstantiationAwareBeanPostProcessorAdapter#postProcessBeforeInitialization(java.lang.Object, java.lang.String)}
		 *
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
