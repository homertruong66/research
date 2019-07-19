package com.hoang.lsp.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationContext implements ApplicationContextAware {

	private static ApplicationContext _context;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		_context = context;
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return _context.getBean(name, requiredType);
	}	
	
}
