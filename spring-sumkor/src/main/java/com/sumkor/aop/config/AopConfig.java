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
@EnableAspectJAutoProxy(proxyTargetClass = false) // 为true使用cglib；为false使用jdk代理
public class AopConfig {

	/**
	 * 定义在service包和所有子包里的任意类的任意方法的执行
	 */
	@Pointcut("execution(* com.sumkor.aop.service..*(..))")
	public void pointCut() {
	}

	/**
	 * context.getBean得到的代理bean的类型，与this相同，则该bean中的所有方法都会执行
	 *
	 * 示例：
	 * MyService myService = context.getBean(MyService.class);
	 * myService.say();
	 *
	 * jdk动态代理时，执行say()之前不会打印before
	 * cglib动态代理时，执行say()之前会打印before
	 *
	 * 原因：
	 * jdk动态代理：bean extends Prosy implement MyService
	 * cglib动态代理：bean extends MyServiceImpl implement MyService
	 */
	@Pointcut("this(com.sumkor.aop.service.MyServiceImpl)")
	public void pointCutThis() {
	}

	/**
	 * context.getBean得到的代理bean的原始类型，与target相同，则该bean中的所有方法都会执行
	 *
	 * 示例：
	 * MyService myService = context.getBean(MyService.class);
	 * myService.say();
	 *
	 * 不管是执行jdk代理还是cglib代理，执行say()之前会打印before
	 */
	@Pointcut("target(com.sumkor.aop.service.MyServiceImpl)")
	public void pointCutTarget() {
	}

	/**
	 * 定义在service包和所有子包里的任意类的play方法的执行
	 */
	@Pointcut("execution(* com.sumkor.aop.service..*play(..))")
	public void pointCutMethod() {
	}

	@Before("pointCutTarget()")
	public void before() {
		System.out.println("before");
	}
}
