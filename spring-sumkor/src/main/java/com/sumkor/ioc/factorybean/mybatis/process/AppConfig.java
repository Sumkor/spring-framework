package com.sumkor.ioc.factorybean.mybatis.process;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Sumkor
 * @since 2020/9/8
 */
@Configuration
@ComponentScan("com.sumkor.ioc.factorybean.mybatis") // IOC扫描
@Import(MyImportBeanDefinitionRegistrar.class) // beanDefinition注册
@MyScan("com.sumkor.ioc.factorybean.mybatis.mapper") // 自定义mapper扫描
public class AppConfig {
}
