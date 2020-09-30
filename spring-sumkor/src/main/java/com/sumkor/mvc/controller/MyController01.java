package com.sumkor.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import javax.servlet.http.HttpServlet;

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
	public String test() {
		return "123";
	}

	/**
	 * 发起 GET 请求，进入 DispatcherServlet
	 *
	 * @see HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @see FrameworkServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @see FrameworkServlet#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @see DispatcherServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @see DispatcherServlet#doDispatch(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *
	 * 1. 根据请求获取 handler
	 * @see DispatcherServlet#getHandler(javax.servlet.http.HttpServletRequest)
	 *
	 * 遍历 handlerMappings，其中具有三种 HandlerMapping 对象：BeanNameUrlHandlerMapping、RequestMappingHandlerMapping、RouterFunctionMapping
	 *
	 * 1.1 命中 RequestMappingHandlerMapping，根据 uri 找到对应的 controller方法，这里为 com.sumkor.mvc.controller.MyController01#test()，将其封装为 HandlerMethod 对象
	 * @see RequestMappingInfoHandlerMapping#getHandlerInternal(javax.servlet.http.HttpServletRequest)
	 * @see AbstractHandlerMethodMapping#getHandlerInternal(javax.servlet.http.HttpServletRequest)
	 * @see AbstractHandlerMethodMapping#lookupHandlerMethod(java.lang.String, javax.servlet.http.HttpServletRequest)
	 *
	 * 2. 利用 handler 查找 adapter
	 * @see DispatcherServlet#getHandlerAdapter(java.lang.Object)
	 *
	 * 遍历 handlerAdapters，其中具有四种 HandlerAdapter 对象：HttpRequestHandlerAdapter、SimpleControllerHandlerAdapter、RequestMappingHandlerAdapter、HandlerFunctionAdapter
	 *
	 * 2.1 命中 RequestMappingHandlerAdapter
	 * @see AbstractHandlerMethodAdapter#supports(java.lang.Object)
	 * @see RequestMappingHandlerAdapter#supportsInternal(org.springframework.web.method.HandlerMethod)
	 *
	 * 3. 执行 controller，入参：Request、Response、HandlerMethod
	 * @see AbstractHandlerMethodAdapter#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 * @see RequestMappingHandlerAdapter#handleInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.web.method.HandlerMethod)
	 * @see RequestMappingHandlerAdapter#invokeHandlerMethod(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.web.method.HandlerMethod)
	 * @see ServletInvocableHandlerMethod#invokeAndHandle(org.springframework.web.context.request.ServletWebRequest, org.springframework.web.method.support.ModelAndViewContainer, java.lang.Object...)
	 * @see InvocableHandlerMethod#doInvoke(java.lang.Object...)
	 */
}
