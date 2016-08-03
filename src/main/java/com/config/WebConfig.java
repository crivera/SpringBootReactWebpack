package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.config.sitemesh.SiteMeshFilter;
import com.utils.Constants;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements EnvironmentAware {

	@Autowired
	Environment env;

	/**
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean siteMeshFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new SiteMeshFilter());
		return filterRegistrationBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #addInterceptors(org
	 * .springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName(Constants.SPRING_LOCAL_PARAMETER);
		registry.addInterceptor(localeChangeInterceptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #addViewControllers(org.springframework.web.servlet.config.annotation.
	 * ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.EnvironmentAware#setEnvironment(org.
	 * springframework.core.env.Environment)
	 */
	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	/**
	 * i18n config
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0);
		return messageSource;
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet servlet = new DispatcherServlet();
		servlet.setDispatchOptionsRequest(true);
		return servlet;
	}
}
