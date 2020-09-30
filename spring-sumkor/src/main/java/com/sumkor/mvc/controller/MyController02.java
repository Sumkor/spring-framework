package com.sumkor.mvc.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * http://localhost:8888/test/app/test02
 *
 * @author Sumkor
 * @since 2020/9/30
 */
@Component("/test02")
public class MyController02 implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		writer.println("123");
		writer.flush();
		return null;
	}
}
