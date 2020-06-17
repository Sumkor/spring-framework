package com.sumkor.ioc.context;

import com.sumkor.ioc.bean.circle.MyBeanA;
import com.sumkor.ioc.bean.circle.MyBeanB;
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
