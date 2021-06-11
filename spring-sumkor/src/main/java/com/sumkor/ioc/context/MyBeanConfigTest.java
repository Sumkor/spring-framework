package com.sumkor.ioc.context;

import com.sumkor.ioc.bean.MyBean;
import com.sumkor.ioc.bean.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Sumkor
 * @since 2021/6/11
 */
public class MyBeanConfigTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.sumkor.ioc.bean.config");
		context.refresh();

		MyConfig myConfig = (MyConfig) context.getBean("myConfig");

		MyBean myBean01 = myConfig.myBean();
		System.out.println("myBean01 = " + myBean01);

		MyBean myBean02 = myConfig.myBean();
		System.out.println("myBean02 = " + myBean02);

		MyBean myBean03 = (MyBean) context.getBean("myBean");
		System.out.println("myBean03 = " + myBean03);

		MyBean myBean04 = (MyBean) context.getBean("myBean");
		System.out.println("myBean04 = " + myBean04);

		/**
		 * > Task :spring-sumkor:MyBeanConfigTest.main()
		 * myBean01 = com.sumkor.ioc.bean.MyBean@6eceb130
		 * myBean02 = com.sumkor.ioc.bean.MyBean@10a035a0
		 * myBean03 = com.sumkor.ioc.bean.MyBean@67b467e9
		 * myBean04 = com.sumkor.ioc.bean.MyBean@67b467e9
		 *
		 * 在 MyConfig 类上：
		 * 如果使用 @Configuration 修饰类，则调用 MyConfig#myBean 方法得到的是单例；
		 * 如果使用 @Component 修饰类，则调用 MyConfig#myBean 方法得到的是多例。
		 *
		 * 如果在 MyConfig#myBean 方法上加上 @Scope("singleton") 来修饰：
		 * 对调用 MyConfig#myBean 方法的结果没有影响；
		 * 对使用 beanName 来获取，得到的是单例（实际上，不加 @Scope 注解的时候默认就是单例）。
		 */
	}
}
