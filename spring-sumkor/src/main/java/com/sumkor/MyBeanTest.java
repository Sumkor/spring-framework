package com.sumkor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Sumkor
 * @since 2020/5/14
 */
public class MyBeanTest {

	public static void main(String[] args) {
		//AnnotationConfigWebApplicationContext
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(MyBean.class);
		context.refresh();

		MyBean myBean = (MyBean) context.getBean("myBean");
		myBean.sayHello();

	}
}
