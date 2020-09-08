package com.sumkor.ioc.factorybean.intro;

import com.sumkor.ioc.bean.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sumkor
 * @since 2020/9/7
 */
@Component
public class MyFactoryBeanService {

	@Autowired
	private MyBean myBean;

	public void sayHello() {
		myBean.sayHello();
	}
}
