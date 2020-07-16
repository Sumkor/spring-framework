package com.sumkor.aop.service;

import org.springframework.stereotype.Component;

/**
 * 没有继承接口
 *
 * @author Sumkor
 * @since 2020/7/16
 */
@Component
public class MyServiceNoFace {

	public void say() {
		System.out.println("Say Hello again");
	}
}
