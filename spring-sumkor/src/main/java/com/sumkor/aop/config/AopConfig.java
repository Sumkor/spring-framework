package com.sumkor.aop.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 黄泽滨 【huangzebin@i72.com】
 * @since 2020/7/16
 */
@Aspect
@Component
public class AopConfig {

	@Pointcut("execution(* com.sumkor.aop.service..*(..))")
	public void pointCut() {
	}

	@Before("pointCut()")
	public void before() {
		System.out.println("before");
	}
}
