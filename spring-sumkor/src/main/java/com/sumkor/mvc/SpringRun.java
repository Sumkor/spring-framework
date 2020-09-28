package com.sumkor.mvc;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/**
 * @author Sumkor
 * @since 2020/9/26
 */
public class SpringRun {

	public static void main(String[] args) {
		new SpringRun().run();
		try {
			Thread.sleep(1000000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8888);
		tomcat.getConnector();

//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		tomcat.setConnector(connector);
//		connector.setPort(8888);
		try {
			//获取项目编译后的 class 路径
			String path = SpringRun.class.getResource("/").getPath();

			//获取webapp 文件
			//String filePath = new File("src/main/webapp").getAbsolutePath();

			//然后将webapp下的项目添加至tomcat的context容器（context对应一个运行的项目）
			Context context = tomcat.addWebapp("/test", "D://html"); //参数1：一般是项目名 对应请求url中的项目名
			//webResourceRoot 用于加载 项目的class文件
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
