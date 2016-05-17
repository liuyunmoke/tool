package com.pipipark.j.mvc.server.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/***
 * 数据源
 * @author pipipark:cwj
 */
@Configuration
@PropertySource({"classpath:database.properties"})
public class DataSourceConfig {
	
	@Value("${jdbc.driver}")  
    String driverClass;
	
    @Value("${jdbc.url}")  
    String url;
    
    @Value("${jdbc.username}")  
    String userName;
    
    @Value("${jdbc.password}")  
    String passWord;
	
	/**
	 * JDBC-Temple.
	 */
	@Bean
	public JdbcTemplate jdbc(){
		return new JdbcTemplate(dataSource());
	}
	
	@Bean(name = "dataSource")  
    public DataSource dataSource() {  
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);
        
//      return (DataSource)jndiObjectFactoryBean().getObject(); //jndi
        return dataSource;
    } 
	
//	@Bean  
//    public JndiObjectFactoryBean jndiObjectFactoryBean(){  
//        JndiObjectFactoryBean factory = new JndiObjectFactoryBean();  
//        factory.setJndiName("xxxx");
//        return factory;  
//    }
}
