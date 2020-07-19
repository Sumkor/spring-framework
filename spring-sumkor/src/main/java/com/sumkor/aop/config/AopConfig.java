package com.sumkor.aop.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 黄泽滨 【huangzebin@i72.com】
 * @since 2020/7/16
 */
@Aspect
@Configuration
@ComponentScan("com.sumkor.aop")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopConfig {

	/**
	 * 定义在service包和所有子包里的任意类的任意方法的执行：
	 */
	@Pointcut("execution(* com.sumkor.aop.service..*(..))")
	public void pointCut() {
	}

	/**
	 * getBean得到的代理bean的类型，与this相同，则该bean中的所有方法都会执行
	 */
	@Pointcut("this(com.sumkor.aop.service.MyServiceImpl)")
	public void pointCutThis() {
	}

	/**
	 * getBean得到的代理bean的原始类型，与target 相同，则该bean中的所有方法都会执行
	 */
	@Pointcut("target(com.sumkor.aop.service.MyServiceImpl)")
	public void pointCutTarget() {
	}

	@Before("pointCutTarget()")
	public void before() {
		System.out.println("before");
	}
}
