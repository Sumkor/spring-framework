package com.sumkor.mvc.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 继承 WebMvcConfigurationSupport，解决 json 视图处理器无法加入问题
 *
 * @author Sumkor
 * @since 2020/9/30
 */
@Configuration
public class MvcConfig /*implements WebMvcConfigurer*/ extends WebMvcConfigurationSupport {

	/**
	 * 设置 JSON 视图
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(jackson2HttpMessageConverter());
	}

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
