package com.sumkor.ioc.bean.circle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sumkor
 * @since 2020/6/16
 */
@Component
public class MyBeanA {

	@Autowired
	private MyBeanB myBeanB;

	public String sayHello() {
		String hello = "hello MyBeanA";
		System.out.println(hello);
		return hello;
	}

	public void sayHelloAgain() {
		myBeanB.sayHello();
	}
}
