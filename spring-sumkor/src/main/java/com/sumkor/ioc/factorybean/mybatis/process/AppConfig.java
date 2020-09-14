package com.sumkor.ioc.factorybean.mybatis.process;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Sumkor
 * @since 2020/9/8
 */
@Configuration
@ComponentScan("com.sumkor.ioc.factorybean.mybatis")
@Import(MyBeanDefinitionRegister.class)
public class AppConfig {
}
