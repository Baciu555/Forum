package com.baciu.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String mismatchHandle(MethodArgumentTypeMismatchException e) {
		return "redirect:/main";
	}
}
