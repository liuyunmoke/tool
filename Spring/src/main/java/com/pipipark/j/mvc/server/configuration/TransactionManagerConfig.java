package com.pipipark.j.mvc.server.configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/***
 * Spring事务管理配置.
 * @author pipipark:cwj
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@Import({DataSourceConfig.class})
public class TransactionManagerConfig {
	
	@Resource(name="dataSource")  
    public DataSource dataSource;
	
	@Bean(name="transactionManager")
	public DataSourceTransactionManager txManager(){
		DataSourceTransactionManager manager = new DataSourceTransactionManager();
		manager.setDataSource(dataSource);
		return manager;
	}
}
