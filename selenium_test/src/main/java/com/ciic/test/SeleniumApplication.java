package com.ciic.test;

import com.ciic.test.service.Proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;


import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@SpringBootApplication
public class SeleniumApplication {
@Autowired
private Proxy proxy;

    /**
     * 主要启动方法
     * @param args
     * @throws IOException
     */
	public static void main(String[] args) throws IOException {

		SpringApplication.run(SeleniumApplication.class, args);


		//System.out.println(123123123);
//		try {
//			new SocketProxy().run();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}


	@Value("${test.file.size}")
	private String fileSize;

    /**
     * 修改上传文件的大小
     * @return
     */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize(fileSize+"KB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize(fileSize+"0KB");
		return factory.createMultipartConfig();
	}

    /**
     * 运行http代理
     * @return
     * @throws IOException
     */
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


