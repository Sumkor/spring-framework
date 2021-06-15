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
		 * 1. 在 MyConfig 类上，如果使用 @Configuration 修饰类，则调用 MyConfig#myBean 方法得到的是单例，且与 beanName 得到的实例相同。
		 * 执行结果如下：
		 * myBean01 = com.sumkor.ioc.bean.MyBean@341b80b2
		 * myBean02 = com.sumkor.ioc.bean.MyBean@341b80b2
		 * myBean03 = com.sumkor.ioc.bean.MyBean@341b80b2
		 * myBean04 = com.sumkor.ioc.bean.MyBean@341b80b2
		 *
		 * 2. 在 MyConfig 类上，如果使用 @Component 修饰类，则调用 MyConfig#myBean 方法得到的是多例，但是 beanName 得到的默认是单例。
		 * 执行结果如下：
		 * myBean01 = com.sumkor.ioc.bean.MyBean@1786f9d5
		 * myBean02 = com.sumkor.ioc.bean.MyBean@704d6e83
		 * myBean03 = com.sumkor.ioc.bean.MyBean@43a0cee9
		 * myBean04 = com.sumkor.ioc.bean.MyBean@43a0cee9
		 *
		 * 3. 在 MyConfig 类中，如果使用 @Configuration 修饰类，在 MyConfig#myBean 方法上加上 @Scope("prototype") 来修饰，则得到多例。
		 * 执行结果如下：
		 * myBean01 = com.sumkor.ioc.bean.MyBean@1f1c7bf6
		 * myBean02 = com.sumkor.ioc.bean.MyBean@25b485ba
		 * myBean03 = com.sumkor.ioc.bean.MyBean@2b546384
		 * myBean04 = com.sumkor.ioc.bean.MyBean@5d740a0f
		 *
		 * 4. 在 MyConfig 类中，如果使用 @Component 修饰类，在 MyConfig#myBean 方法上加上 @Scope("prototype") 来修饰，同样得到多例。
		 * 执行结果如下：
		 * myBean01 = com.sumkor.ioc.bean.MyBean@3578436e
		 * myBean02 = com.sumkor.ioc.bean.MyBean@706a04ae
		 * myBean03 = com.sumkor.ioc.bean.MyBean@67b467e9
		 * myBean04 = com.sumkor.ioc.bean.MyBean@47db50c5
		 */
	}
}
