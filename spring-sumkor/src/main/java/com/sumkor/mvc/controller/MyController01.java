package com.sumkor.mvc.controller;

import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import javax.servlet.http.HttpServlet;

/**
 * 使用@Controller注解实现controller
 *
 * @author Sumkor
 * @since 2020/9/26
 */
@Controller
@RequestMapping("/test01")
public class MyController01 {

	/**
	 * http://localhost:8888/test/app/test01/test01
	 * <p>
	 * 返回字符串
	 */
	@RequestMapping("/test01")
	@ResponseBody
	public String test01() {
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
	 * 1. 根据请求获取 handler 执行链
	 * @see DispatcherServlet#getHandler(javax.servlet.http.HttpServletRequest)
	 *
	 * 遍历 handlerMappings，其中具有三种 HandlerMapping 对象：BeanNameUrlHandlerMapping、RequestMappingHandlerMapping、RouterFunctionMapping
	 *
	 * 1.1 命中 RequestMappingHandlerMapping
	 * @see AbstractHandlerMapping#getHandler(javax.servlet.http.HttpServletRequest)
	 *
	 * A. 根据 uri 找到对应的 controller方法，这里为 com.sumkor.mvc.controller.MyController01#test()，将其封装为 HandlerMethod 对象
	 * @see RequestMappingInfoHandlerMapping#getHandlerInternal(javax.servlet.http.HttpServletRequest)
	 * @see AbstractHandlerMethodMapping#getHandlerInternal(javax.servlet.http.HttpServletRequest)
	 * @see AbstractHandlerMethodMapping#lookupHandlerMethod(java.lang.String, javax.servlet.http.HttpServletRequest)
	 *
	 * B. 获取所有的interceptor的对象，然后与当前的handler组合在一起，构成 HandlerExecutionChain 执行链
	 * @see AbstractHandlerMapping#getHandlerExecutionChain(java.lang.Object, javax.servlet.http.HttpServletRequest)
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
	 * 3. 执行 controller 并处理返回结果
	 * @see AbstractHandlerMethodAdapter#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 * @see RequestMappingHandlerAdapter#handleInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.web.method.HandlerMethod)
	 * @see RequestMappingHandlerAdapter#invokeHandlerMethod(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.web.method.HandlerMethod)
	 * @see ServletInvocableHandlerMethod#invokeAndHandle(org.springframework.web.context.request.ServletWebRequest, org.springframework.web.method.support.ModelAndViewContainer, java.lang.Object...)
	 *
	 * 3.1 执行 controller，入参：Request、Response、HandlerMethod
	 * @see InvocableHandlerMethod#invokeForRequest(org.springframework.web.context.request.NativeWebRequest, org.springframework.web.method.support.ModelAndViewContainer, java.lang.Object...)
	 * @see InvocableHandlerMethod#doInvoke(java.lang.Object...)
	 *
	 * 3.2 处理返回结果
	 * @see HandlerMethodReturnValueHandlerComposite#handleReturnValue(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest)
	 *
	 * 这里选择到了 RequestResponseBodyMethodProcessor，利用它处理返回结果
	 * @see RequestResponseBodyMethodProcessor#handleReturnValue(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest)
	 *
	 * 得到 MediaType 为 'text/html'，body 为 '123'，将其写入 response
	 * @see AbstractMessageConverterMethodProcessor#writeWithMessageConverters(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.http.server.ServletServerHttpRequest, org.springframework.http.server.ServletServerHttpResponse)
	 * @see AbstractHttpMessageConverter#write(java.lang.Object, org.springframework.http.MediaType, org.springframework.http.HttpOutputMessage)
	 */
}
