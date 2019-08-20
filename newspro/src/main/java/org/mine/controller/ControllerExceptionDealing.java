package org.mine.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionDealing {

	@ExceptionHandler(Exception.class)
	public ModelAndView dealProcessing(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		
		
		
		return new ModelAndView("error");
	}
	
}
