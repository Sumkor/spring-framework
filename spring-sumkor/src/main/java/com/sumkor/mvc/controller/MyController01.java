package com.sumkor.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * http://localhost:8888/test/app/test01/test
 *
 * @author Sumkor
 * @since 2020/9/26
 */
@Controller
@RequestMapping("/test01")
public class MyController01 {

	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		return "123";
	}
}
