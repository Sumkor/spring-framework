package com.sumkor.aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Sumkor
 * @since 2020/7/16
 */
@Configuration
@ComponentScan("com.sumkor.aop")
@EnableAspectJAutoProxy
public class AppConfig {
}
