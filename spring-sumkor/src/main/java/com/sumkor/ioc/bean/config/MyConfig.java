package com.sumkor.ioc.bean.config;

import com.sumkor.ioc.bean.MyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author Sumkor
 * @since 2021/6/11
 */
@Component
//@Configuration
public class MyConfig {

	@Bean
	//@Scope("singleton") // 默认值
	@Scope("prototype")
	public MyBean myBean() {
		return new MyBean();
	}
}
