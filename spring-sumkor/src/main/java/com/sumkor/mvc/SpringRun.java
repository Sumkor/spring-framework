package com.sumkor.mvc;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/**
 * SpringMVC启动入口
 *
 * @author Sumkor
 * @since 2020/9/26
 */
public class SpringRun {

	public static void main(String[] args) {
		new SpringRun().run();
	}

	/**
	 * 使用 Tomcat容器启动 mvc
	 * 参考：spring mvc 源码解读一
	 * https://blog.csdn.net/sonycong/article/details/91891841
	 */
	public void run() {
		Tomcat tomcat = new Tomcat();
		Connector connector = new Connector();
		connector.setPort(8888);
		tomcat.setConnector(connector);
		try {
			// 设置tomcat的context容器（context对应一个运行的项目）
			Context context = tomcat.addWebapp("/test", "../"); // contextPath：对应请求url中的项目名；docBase：这里指向spring-framework项目目录
			// 获取项目编译后的 class 路径
			String path = SpringRun.class.getResource("/").getPath();
			// webResourceRoot 用于加载 项目的class文件
			WebResourceRoot webResource = new StandardRoot(context);
			webResource.addPreResources(new DirResourceSet(webResource, "/WEB-INF/classes", path, "/"));
			tomcat.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//阻塞 ，等待前端连接
		tomcat.getServer().await();
	}
}
