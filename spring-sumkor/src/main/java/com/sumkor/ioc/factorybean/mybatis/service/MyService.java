package com.sumkor.ioc.factorybean.mybatis.service;

import com.sumkor.ioc.factorybean.mybatis.mapper.OrderMapper;
import com.sumkor.ioc.factorybean.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sumkor
 * @since 2020/9/8
 */
@Component
public class MyService {

	@Autowired
	private UserMapper userMapper;

//	@Autowired
//	private OrderMapper orderMapper;

	public void execute() {
		System.out.println("execute userMapper.selectById");
		userMapper.selectById(1L);

//		System.out.println("execute orderMapper.selectById");
//		orderMapper.selectById(1L);
	}
}
