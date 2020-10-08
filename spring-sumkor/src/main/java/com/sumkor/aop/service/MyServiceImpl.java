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
		System.out.println("say 中文");
	}

	@Override
	public void play() {
		System.out.println("play 中文");
	}
}
