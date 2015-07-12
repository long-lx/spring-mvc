package edu.java.spring.view;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

public class CustomXsltViewResolver extends XsltViewResolver {
	
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		
		if (viewName.startsWith("Student")) return null;
		return super.resolveViewName(viewName, locale);
	}
}
