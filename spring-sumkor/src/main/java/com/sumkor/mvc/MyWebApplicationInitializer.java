package com.sumkor.mvc;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ContextConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


/**
 * 使用 WebApplicationInitializer 替代 web.xml，如何使 web 容器在启动时加载 web.xml 配置文件？
 * 利用SPI机制，当 tomcat 启动时，扫描项目 META-INF/services 目录下的文件，执行 ServletContainerInitializer.onStartup 方法使得 web 容器能够加载到 web.xml 配置
 *
 * @see javax.servlet.ServletContainerInitializer
 * @see org.springframework.web.SpringServletContainerInitializer
 *
 * 深入了解WebApplicationInitializer是如何消除web.xml和springMVC的配置文件
 * https://www.nonelonely.com/article/1552475062917
 *
 * @author Sumkor
 * @since 2020/9/25
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// Load Spring web application configuration
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.scan("com.sumkor.mvc");
		context.refresh();

		// Create and register the DispatcherServlet
		DispatcherServlet servlet = new DispatcherServlet(context);
		ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet); // 注册servlet实例，设置servletName
		registration.setLoadOnStartup(1);
		registration.addMapping("/app/*"); // 类似web.xml中的servlet-mapping标签，将匹配url的请求映射到servlet上
	}
	/**
	 * tomcat 加载 web.xml 流程：
	 *
	 * tomcat扫描web.xml，其中执行 context.addServletContainerInitializer
	 * @see ContextConfig#webConfig()
	 *
	 * tomcat启动web容器，加载web.xml
	 * @see StandardContext#startInternal()
	 */
}
