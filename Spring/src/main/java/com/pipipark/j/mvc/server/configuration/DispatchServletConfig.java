package com.pipipark.j.mvc.server.configuration;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.pipipark.j.mvc.PPPMvcInitializer;
import com.pipipark.j.mvc.PPPMvcInterceptor;
import com.pipipark.j.mvc.server.scaner.PPPInterceptorScaner;
import com.pipipark.j.system.annotation.PPPExclude;
import com.pipipark.j.system.classscan.v2.PPPClassesScaner;
import com.pipipark.j.system.classscan.v2.PPPScanEntity;
import com.pipipark.j.system.classscan.v2.PPPScanerManager;
import com.pipipark.j.system.core.PPPConstant;

/***
 * SpringMVC配置.
 * @author pipipark:cwj
 */
@Configuration
@PPPExclude
@EnableWebMvc
@ComponentScan(basePackages = {"com"})
public class DispatchServletConfig extends WebMvcConfigurationSupport {

	/**
	 * 视图处理器
	 * @return InternalResourceViewResolver
	 */
	@Bean 
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
	
//	/**
//	 * 国际化资源文件
//	 * @return ResourceBundleMessageSource
//	 */
//	@Bean  
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();  
//        messageSource.setBasename("messages");  
//        return messageSource;  
//    }
	
	/**
	 * 上传文件,
	 * 限制上传最大 20M,
	 * 最大缓存 40KB
	 * @return CommonsMultipartResolver
	 */
	@Bean
    public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver fileUpload = new CommonsMultipartResolver(); 
		fileUpload.setDefaultEncoding(PPPConstant.Systems.DEFAULT_CHARSET);
		fileUpload.setMaxUploadSize(20971520);
		fileUpload.setMaxInMemorySize(40960);
        return fileUpload;  
    }
	
	/**
	 * 使用spring管理servlet,
	 * 设置后可将servlet注册为bean.
	 * @return SimpleServletHandlerAdapter
	 */
	@Bean  
    public HandlerAdapter servletHandlerAdapter(){
        return new SimpleServletHandlerAdapter();
    }
	
	/**
	 * 本地化拦截器.
	 * @return LocaleChangeInterceptor
	 */
	@Bean  
    public LocaleChangeInterceptor localeChangeInterceptor(){
        return new LocaleChangeInterceptor();  
    }
	
	/**
	 * 配置允许自定义拦截器.
	 * @return RequestMappingHandlerMapping
	 */
	@Bean  
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return super.requestMappingHandlerMapping();  
    }
	
	@Override  
    protected void addInterceptors(InterceptorRegistry registry) {
		//搜索自定义拦截器
        registry.addInterceptor(localeChangeInterceptor());
		PPPClassesScaner<PPPMvcInterceptor> scaner = (PPPClassesScaner<PPPMvcInterceptor>)PPPScanerManager.scaner(PPPInterceptorScaner.class);
		Set<PPPScanEntity> set = scaner.getData();
        for (PPPScanEntity interceptor : set) {
        	registry.addInterceptor((PPPMvcInterceptor)interceptor.getTarget());
		}
    }
	
	/**
	 * 配置资源访问器
	 */
	@Bean  
    public HandlerMapping resourceHandlerMapping() {
        return super.resourceHandlerMapping();  
    }
	@Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("/WEB-INF/resource/");
        registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/html");
    }
}
