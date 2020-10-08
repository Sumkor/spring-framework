package com.sumkor.aop.service;

import org.springframework.stereotype.Component;

/**
 * 没有继承接口，照样可以被AOP动态代理
 *
 * @author Sumkor
 * @since 2020/7/16
 */
@Component
public class MyServiceNoFace {

	public void say() {
		System.out.println("Say 中文 again");
	}
}
