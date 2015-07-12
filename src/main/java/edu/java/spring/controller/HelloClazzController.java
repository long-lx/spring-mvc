package edu.java.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello")

public class HelloClazzController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView printMessage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("infor");
		mav.addObject("message", "Hello Java Clazz!");
		return mav;
	}
}