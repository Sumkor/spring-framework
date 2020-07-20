package com.sumkor.ioc.factorybean;

import com.sumkor.ioc.bean.MyBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 一个能生产或者修饰对象生成的工厂Bean，它的实现与设计模式中的工厂模式和修饰器模式类似
 *
 * @author Sumkor
 * @since 2020/7/20
 */
@Component
public class MyFactoryBean implements FactoryBean<MyBean> {

	/**
	 * 工厂bean 具体创建具体对象是由此getObject()方法来返回的
	 */
	@Override
	public MyBean getObject() {
		return new MyBean();
	}

	@Override
	public Class<?> getObjectType() {
		return MyBean.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
