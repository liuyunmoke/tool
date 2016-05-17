package com.pipipark.j.mvc.core;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.core.PPPString;


/***
 * Spring框架工具类.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPContext implements ApplicationContextAware,IPPPark {
	
	private static Properties properties;
	
	/**
	 * mvc服务类默认(约定)执行的方法名.
	 */
	public static final String EXECUTE_METHOD="execute";
	
	@Value("#{properties}")
	public void setProperties(Properties p){
		properties = p;
	}
	
	/**
	 * 获取properties属性值
	 * @param key
	 * @return value
	 */
	public static String properties(String key){
		Object val = properties.get(key);
		if(val!=null){
			return val.toString();
		}
		return null;
	}
	
	private static ApplicationContext _applicationContext;
	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 * @throws BeansException
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		_applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext applicationContext() {
		return _applicationContext;
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name){
		Object bean;
		try{
			bean = _applicationContext.getBean(name);
			if(bean==null){
				bean = _applicationContext.getBean(PPPString.md5(name));
			}
		}catch(BeansException e){
			bean = _applicationContext.getBean(PPPString.md5(name));
		}
		return bean;
	}

	/**
	 * 获取类型为requiredType的对象
	 * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
	 * 
	 * @param name
	 *            bean注册名
	 * @param requiredType
	 *            返回对象类型
	 * @return Object 返回requiredType类型对象
	 * @throws BeansException
	 */
	public static <M> M getBean(String name, Class<M> requiredType){
		M bean;
		try{
			bean = _applicationContext.getBean(name, requiredType);
			if(bean==null){
				bean = _applicationContext.getBean(PPPString.md5(name), requiredType);
			}
		}catch(BeansException e){
			bean = _applicationContext.getBean(PPPString.md5(name), requiredType);
		}
		return bean;
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		boolean bool = _applicationContext.containsBean(name);
		if(!bool){
			bool = _applicationContext.containsBean(PPPString.md5(name));
		}
		return bool;
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {
		boolean bool;
		try{
			bool = _applicationContext.isSingleton(name);
		}catch(NoSuchBeanDefinitionException e){
			bool = _applicationContext.isSingleton(PPPString.md5(name));
		}
		return bool;
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name)
			throws NoSuchBeanDefinitionException {
		Class<?> clazz = _applicationContext.getType(name);
		if(clazz==null){
			return _applicationContext.getType(PPPString.md5(name));
		}
		return clazz;
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name)
			throws NoSuchBeanDefinitionException {
		String[] temp = _applicationContext.getAliases(name);
		if(temp==null||temp.length==0){
			return _applicationContext.getAliases(PPPString.md5(name));
		}
		return temp;
	}

	/**
	 * 动态注册Bean
	 * @param service
	 */
	public static void addBean(Class<?> beanClass) {
		String name = PPPString.md5(PPPString.aliasName(beanClass));
		if (!_applicationContext.containsBean(name)) {
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
					.genericBeanDefinition(beanClass);
			ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) _applicationContext;
			BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
					.getBeanFactory();
			beanDefinitonRegistry.registerBeanDefinition(name,
					beanDefinitionBuilder.getRawBeanDefinition());
		}
	}
	public static void addBean(String name, Class<?> beanClass) {
		name = PPPString.md5(name);
		if (!_applicationContext.containsBean(name)) {
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
					.genericBeanDefinition(beanClass);
			ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) _applicationContext;
			BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
					.getBeanFactory();
			beanDefinitonRegistry.registerBeanDefinition(name,
					beanDefinitionBuilder.getRawBeanDefinition());
		}
	}
	
	public static void addService(Class<?> beanClass){
		String name = PPPString.aliasName(beanClass);
		addBean(name, beanClass);
	}
	public static void addService(String name, Class<?> beanClass){
		addBean(name, beanClass);
	}
	public static Object getService(String name){
		return getBean(name);
	}
	public static <M> M getService(String name, Class<M> requiredType){
		return getBean(name, requiredType);
	}
	
}
