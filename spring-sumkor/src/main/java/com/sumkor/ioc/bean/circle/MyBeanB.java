package com.sumkor.ioc.bean.circle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sumkor
 * @since 2020/6/16
 */
@Component
public class MyBeanB {

	@Autowired
	private MyBeanA myBeanA;

	public String sayHello() {
		String hello = "hello MyBeanB";
		System.out.println(hello);
		return hello;
	}

	public void sayHelloAgain() {
		myBeanA.sayHello();
	}
}
