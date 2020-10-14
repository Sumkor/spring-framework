package com.sumkor.ioc.factorybean.intro;

import com.sumkor.ioc.bean.MyBean;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * Spring FactoryBean和BeanFactory 区别
 * https://blog.csdn.net/weixin_38361347/article/details/92852611
 * BeanFactory和FactoryBean其实没有什么比较性的，只是两者的名称特别接近，所以有时候会拿出来比较一番。
 * BeanFactory是IOC容器的底层实现接口，是ApplicationContext顶级接口，给具体的IOC容器的实现提供了规范。
 * FactoryBean是个bean，在IOC容器的基础上给Bean的实现加上了一个简单工厂模式和装饰模式，是一个可以生产对象和装饰对象的工厂bean
 *
 * 除了FactoryBean之外，其他的将Bean注册到Spring之中的方式：
 * 1. {@link SingletonBeanRegistry#registerSingleton(java.lang.String, java.lang.Object)}
 * 2. @Component、@Bean、@Import
 *
 * @author Sumkor
 * @since 2020/7/20
 */
public class FactoryBeanTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.sumkor.ioc.factorybean.intro");
		context.refresh();
		/**
		 * 将 MyFactoryBean 实例放入单例池，beanName 为 'myFactoryBean'，没有&前缀
		 * {@link DefaultListableBeanFactory#preInstantiateSingletons()}
		 * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
		 */

		// context.getBean("myBean");// No bean named 'myBean' available

		// 通过调用FactoryBean的beanName获取目标对象
		Object bean01 = context.getBean("myFactoryBean");// 实际由Spring容器调用FactoryBean.getObject方法
		System.out.println(bean01 instanceof MyBean);
		/**
		 * 1.根据 beanName 'myFactoryBean' 从单例池中取出 MyFactoryBean 实例
		 * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
		 *
		 * 2.根据 beanName 'myFactoryBean' 从 factoryBeanObjectCache 取出 MyBean 实例
		 * {@link AbstractBeanFactory#getObjectForBeanInstance(java.lang.Object, java.lang.String, java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition)}
		 *
		 * 2.1 如果 factoryBeanObjectCache 中存在，则直接取出（例如 MyFactoryBeanService中注入了 MyBean，获取 MyBean实例 时提前生成并放入缓存）
		 * {@link FactoryBeanRegistrySupport#getCachedObjectForFactoryBean(java.lang.String)}
		 *
		 * 2.2 如果 factoryBeanObjectCache 中不存在，则调用 FactoryBean.getObject 方法生成 MyBean，存入缓存并返回
		 * {@link FactoryBeanRegistrySupport#getObjectFromFactoryBean(org.springframework.beans.factory.FactoryBean, java.lang.String, boolean)}
		 * {@link FactoryBeanRegistrySupport#doGetObjectFromFactoryBean(org.springframework.beans.factory.FactoryBean, java.lang.String)}
		 *
		 *
		 * 注意！！！
		 * MyFactoryBean 实例存储在 {@link DefaultSingletonBeanRegistry#singletonObjects} 单例池中，key 为 beanName 'myFactoryBean'
		 * MyBean 实例不会存储在单例池，存储在 {@link FactoryBeanRegistrySupport#factoryBeanObjectCache} 缓存中，key 为 beanName 'myFactoryBean'
		 */

        // 单例
		Object bean02 = context.getBean("myFactoryBean");
		System.out.println("bean01 = " + bean01);// com.sumkor.ioc.bean.MyBean@76b0bfab
		System.out.println("bean02 = " + bean02);// com.sumkor.ioc.bean.MyBean@76b0bfab

		// 通过调用&FactoryBean的beanName获取FactoryBean对象
		Object bean03 = context.getBean("&myFactoryBean");
		System.out.println(bean03 instanceof MyFactoryBean);

		// ----------------------------------------------------------------------

//		// 目标对象可以通过@Autowired注入：优先用byType，而后是byName https://blog.csdn.net/yangjiachang1203/article/details/52128830
//		MyFactoryBeanService factoryBeanService = context.getBean(MyFactoryBeanService.class);
//		factoryBeanService.sayHello();

		// ----------------------------------------------------------------------

		// 直接通过调用FactoryBean.getObject获取目标对象。返回的是新创建的对象，不是缓存在 factoryBeanObjectCache 中的单例
		MyFactoryBean myFactoryBean = context.getBean(MyFactoryBean.class);
		MyBean myBean01 = myFactoryBean.getObject();
		Assert.notNull(myBean01, "myFactoryBean.getObject not null");
		myBean01.sayHello();
		System.out.println("myBean01 = " + myBean01);// com.sumkor.ioc.bean.MyBean@75881071

		// 直接通过MyBean类获取目标对象
		MyBean myBean02 = context.getBean(MyBean.class);
		myBean02.sayHello();
		System.out.println("myBean02 = " + myBean02);// com.sumkor.ioc.bean.MyBean@76b0bfab
		/**
		 * 根据 BeanType 获取 bean 实例
		 * @see DefaultListableBeanFactory#resolveBean(org.springframework.core.ResolvableType, java.lang.Object[], boolean)
		 * @see DefaultListableBeanFactory#resolveNamedBean(org.springframework.core.ResolvableType, java.lang.Object[], boolean)
		 *
		 * 1.1 根据 BeanType 获取 beanName。这里检查到 beanName 'myFactoryBean' 可以匹配 MyBean.class
		 * @see DefaultListableBeanFactory#getBeanNamesForType(org.springframework.core.ResolvableType, boolean, boolean)
		 * @see DefaultListableBeanFactory#getBeanNamesForType(java.lang.Class, boolean, boolean)
		 *
		 * 遍历所有的 beanDefinitionNames(beanName 集合)，判断 beanName 与 beanType 是否符合
		 * @see DefaultListableBeanFactory#doGetBeanNamesForType(org.springframework.core.ResolvableType, boolean, boolean)
		 *
		 * 根据 beanName 'myFactoryBean' 获取到 myFactoryBean 实例，调用 FactoryBean.getObject 获得 MyBean 类型，用于判断类型是否符合
		 * @see AbstractBeanFactory#isTypeMatch(java.lang.String, org.springframework.core.ResolvableType, boolean)
		 * @see FactoryBeanRegistrySupport#getTypeForFactoryBean(org.springframework.beans.factory.FactoryBean)
		 *
		 * 1.2 根据获取到的 beanName 'myFactoryBean'，从单例池中获取 MyFactoryBean 实例，再从 factoryBeanObjectCache 获取 MyBean 实例
		 * @see AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
		 * @see AbstractAutowireCapableBeanFactory#getObjectForBeanInstance(java.lang.Object, java.lang.String, java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition)
		 */
	}
}
