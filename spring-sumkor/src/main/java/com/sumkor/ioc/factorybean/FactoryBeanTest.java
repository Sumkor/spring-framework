package com.sumkor.ioc.factorybean;

import com.sumkor.ioc.bean.MyBean;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
		context.register(MyFactoryBean.class);
		context.refresh();

		MyFactoryBean myFactoryBean = context.getBean(MyFactoryBean.class);
		MyBean myBean = myFactoryBean.getObject();
		myBean.sayHello();

		Object bean01 = context.getBean("myFactoryBean");
		System.out.println(bean01 instanceof MyBean);// 实际由Spring容器调用FactoryBean.getObject方法

		Object bean02 = context.getBean("&myFactoryBean");
		System.out.println(bean02 instanceof MyFactoryBean);
	}
}
