package com.sumkor.aop.service;

import org.springframework.stereotype.Service;

/**
 * @author Sumkor
 * @since 2020/7/16
 */
@Service
public class MyServiceImpl implements MyService {

	@Override
	public void say() {
		System.out.println("hello 中文");
	}
}
