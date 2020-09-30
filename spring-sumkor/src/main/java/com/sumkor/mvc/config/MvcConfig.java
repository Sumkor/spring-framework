package com.sumkor.mvc.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Sumkor
 * @since 2020/9/30
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/**
	 * 设置 JSON 视图
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(fastJsonHttpMessageConverter());
	}
//
//	@Bean
//	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter(){
//		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		// Long 转 String
//		SimpleModule simpleModule = new SimpleModule();
//		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//		objectMapper.registerModule(simpleModule);
//		jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//		return jackson2HttpMessageConverter;
//	}

	@Bean
	public HttpMessageConverter<?> fastJsonHttpMessageConverter(){
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteDateUseDateFormat);

		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		return fastJsonHttpMessageConverter;
	}
}
