package com.sumkor.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

/**
 * SpringMVC 的 Controller 返回各种视图的处理方式
 * https://www.cnblogs.com/xuyuanjia/p/5721677.html
 *
 * @author Sumkor
 * @since 2020/9/30
 */
@Controller
@RequestMapping("/json")
public class MyJsonController {

	/**
	 * http://localhost:8888/test/app/json/test02
	 *
	 * 如果没有配置JSON视图，则报错：
	 * 警告: Resolved [org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class java.util.HashMap]
	 */
	@RequestMapping("/test00")
	@ResponseBody
	public Map<String, Object> test00() {
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", 123);
		return map;
	}

	/**
	 * http://localhost:8888/test/app/json/test01
	 *
	 * 返回ModelAndView，手动设置MappingJackson2JsonView
	 */
	@RequestMapping("/test01")
	@ResponseBody
	public ModelAndView test01() {
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", 123);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * http://localhost:8888/test/app/json/test02
	 *
	 * 返回JSON字符串，响应头是'text/html'
	 */
	@RequestMapping("/test02")
	@ResponseBody
	public String test02() {
		return "{\"result\":true}";
	}

}
