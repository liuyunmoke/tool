package com.pipipark.j.mvc.server.configuration;

import java.io.IOException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.pipipark.j.mvc.core.PPPContext;
import com.pipipark.j.mvc.server.processor.BeanAfterInitProcessor;
import com.pipipark.j.mvc.server.processor.BeanBeforeInitProcessor;
import com.pipipark.j.system.core.PPPConstant;

/***
 * Bean注入配置.
 * 
 * @author pipipark:cwj
 */
@Configuration
public class BaseBeansConfig {

	/**
	 * 上下文应用工具 
	 */
	@Bean
	public PPPContext springContext() {
		return new PPPContext();
	}

	/**
	 * Bean初始化前置功能
	 */
	@Bean
	public BeanBeforeInitProcessor beanBeforeInitProcessor() {
		return new BeanBeforeInitProcessor();
	}
	/**
	 * Bean初始化后置功能
	 */
	@Bean
	public BeanAfterInitProcessor beanAfterInitProcessor() {
		return new BeanAfterInitProcessor();
	}

	/**
	 * 资源文件Bean
	 */
	@Bean
	public PropertiesFactoryBean properties() throws IOException {
		PropertiesFactoryBean p = new PropertiesFactoryBean();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources;
		resources = resolver.getResources("classpath*:**/*.properties");
		if (resources == null) {
			throw new IOException("class path resource no found.");
		}
		p.setLocations(resources);
		p.setFileEncoding(PPPConstant.Systems.DEFAULT_CHARSET);
		return p;
	}
	
	@Bean  
    public PropertySourcesPlaceholderConfigurer placehodlerConfigurer() throws IOException {
		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
		c.setProperties(properties().getObject());
        return c;
    }
	
}
