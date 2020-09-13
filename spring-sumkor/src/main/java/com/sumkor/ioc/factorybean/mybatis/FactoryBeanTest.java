package com.sumkor.ioc.factorybean.mybatis;

import com.sumkor.ioc.factorybean.mybatis.service.MyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Sumkor
 * @since 2020/9/8
 */
public class FactoryBeanTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		MyService myService = context.getBean("myService", MyService.class);
		myService.execute();
	}
}
