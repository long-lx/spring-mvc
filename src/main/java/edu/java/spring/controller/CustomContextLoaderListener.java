package edu.java.spring.controller;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import edu.java.spring.dao.StudentDAO;

public class CustomContextLoaderListener extends ContextLoaderListener {	
	
	@Override
	public void contextDestroyed (ServletContextEvent event) {
//		System.out.println("\n Spring-MVC application destroyed \n");
//		super.contextDestroyed(event);
		getCurrentWebApplicationContext().getBean(StudentDAO.class).shutdown();
	}
	
	@Override
	public void contextInitialized (ServletContextEvent event) {
		System.out.println("\n Spring-MVC application inited \n");
		super.contextInitialized(event);
	}
}
