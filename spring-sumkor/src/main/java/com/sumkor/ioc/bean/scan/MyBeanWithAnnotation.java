package com.sumkor.ioc.bean.scan;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Sumkor
 * @since 2020/6/15
 */
@Component
public class MyBeanWithAnnotation {

	public void sayHello() {
		System.out.println("hello 中文");
	}

	@Bean("aBean")
	public String getABean() {
		return "哈哈";
	}
}
