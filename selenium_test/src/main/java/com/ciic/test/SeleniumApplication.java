package com.ciic.test;

import com.ciic.test.service.Proxy;
import com.ciic.test.tools.SocketProxy;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@SpringBootApplication
public class SeleniumApplication {
@Autowired
private Proxy proxy;

	public static void main(String[] args) throws IOException {

		SpringApplication.run(SeleniumApplication.class, args);


		//System.out.println(123123123);
//		try {
//			new SocketProxy().run();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize("10240KB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("102400KB");
		return factory.createMultipartConfig();
	}
@Bean
public int proxy() throws IOException {
		proxy.run();


		return 1;
}


//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer(){
//		return new EmbeddedServletContainerCustomizer() {
//			@Override
//			public void customize(ConfigurableEmbeddedServletContainer Container) {
//				Container.setSessionTimeout(60*60);//单位为S
//			}
//		};
//	}
}


