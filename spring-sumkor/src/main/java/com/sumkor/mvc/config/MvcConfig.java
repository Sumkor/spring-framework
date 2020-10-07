package com.sumkor.mvc.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 如何使 MvcConfig 生效？
 * 重要前提，SpringContext 中设置 ServletContext，并将 MvcConfig 配置以下其中之一：
 * 1. 继承 WebMvcConfigurationSupport
 * 2. 实现 WebMvcConfigurer，并使用注解 @EnableWebMvc
 * <p>
 * WebMvcConfigurationSupport与WebMvcConfigurer的关系
 * https://blog.csdn.net/qq_39385118/article/details/86670490
 * 为什么 WebMvcConfigurer 实现要加 @EnableWebMvc 注解？
 * EnableWebMvc 注解类上导入了 DelegatingWebMvcConfiguration 类，该类是 WebMvcConfigurationSupport 的子类，
 * 该类除了实例化 WebMvcConfigurationSupport 以外，另一个作用就是收集 BeanFactory 中所有 WebMvcConfigurer 的实现，汇集到 WebMvcConfigurerComposite 中，
 * 在 WebMvcConfigurationSupport 实例化过程中会分别调用这些实现，将相应的实例传入这些实现中，供开发者在此基础上添加自定义的配置。
 * 这也就是在 WebMvcConfigurerAdapter 子类上要加 @EnableWebMvc 的原因，因为要先实例化 WebMvcConfigurationSupport。
 *
 * @author Sumkor
 * @see WebMvcConfigurer
 * @see EnableWebMvc
 * @see WebMvcConfigurationSupport
 * @since 2020/9/30
 */
@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer /*extends WebMvcConfigurationSupport */ {

//	/**
//	 * MvcConfig 的 bean 的生命周期中，执行 BeanPostProcessor 设置 ServletContext
//	 * @see ServletContextAwareProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
//	 */
//	@Override
//	public void setServletContext(ServletContext servletContext) {
//		super.setServletContext(servletContext);
//	}

	/**
	 * 将自定的视图处理器，注册到 mvc。如何触发注册？
	 *
	 * 1. 若 MvcConfig 继承 WebMvcConfigurationSupport：
	 *
	 * 由于 WebMvcConfigurationSupport 中包含了 @Bean 注解的方法，实例化 RouterFunctionMapping 时调用了 MvcConfig#configureMessageConverters
	 * @see WebMvcConfigurationSupport#routerFunctionMapping(org.springframework.format.support.FormattingConversionService, org.springframework.web.servlet.resource.ResourceUrlProvider)
	 *
	 * 2. 若 MvcConfig 实现 WebMvcConfigurer，并使用注解 @EnableWebMvc：
	 *
	 * 由于 @EnableWebMvc 中导入了 DelegatingWebMvcConfiguration 类，Spring 启动时会处理 @Import 注解，解析为 BeanDefinition 进行注册，最后实例化 DelegatingWebMvcConfiguration
	 *
	 * 具体流程为：
	 * 2.1 处理 @Import 注解
	 * @see AbstractApplicationContext#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 * @see ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)
	 * @see org.springframework.context.annotation.ConfigurationClassParser#processImports(org.springframework.context.annotation.ConfigurationClass, org.springframework.context.annotation.ConfigurationClassParser.SourceClass, java.util.Collection, java.util.function.Predicate, boolean)
	 * 2.2 将 DelegatingWebMvcConfiguration 注册为 BeanDefinition
	 * 2.3 之后遍历所有的 BeanDefinition 进行实例化
	 * @see AbstractApplicationContext#finishBeanFactoryInitialization(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 * @see DefaultListableBeanFactory#preInstantiateSingletons()
	 *
	 * 由于 DelegatingWebMvcConfiguration 是 WebMvcConfigurationSupport 的子类，因此也会处理 WebMvcConfigurationSupport 中的 @Bean注解，这里跟第 1 点相同
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(jackson2HttpMessageConverter());
	}

	/**
	 * 定义 JSON 视图处理器，支持将 controller 返回值序列化为 JSON
	 */
	@Bean
	public HttpMessageConverter<?> jackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// Long 转 String
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
		jackson2HttpMessageConverter.setObjectMapper(objectMapper);
		return jackson2HttpMessageConverter;
	}

//	@Bean
//	public HttpMessageConverter<?> fastJsonHttpMessageConverter(){
//		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//		FastJsonConfig fastJsonConfig = new FastJsonConfig();
//		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
//				SerializerFeature.WriteMapNullValue,
//				SerializerFeature.WriteNullStringAsEmpty,
//				SerializerFeature.DisableCircularReferenceDetect,
//				SerializerFeature.WriteNullListAsEmpty,
//				SerializerFeature.WriteDateUseDateFormat);
//
//		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//		return fastJsonHttpMessageConverter;
//	}
}
