package com.poscoict.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String ExceptionHandler(Model model, Exception e) {
		//1. 로깅
		//String errors = e.toString(); 안댐
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		System.out.println(errors.toString());

		model.addAttribute("exception", errors.toString());
		
		//e.printStackTrace(); //화면으로나와
		
		//2. 사과페이지(HTML응ㅇ답, 정상종료)
		
		return "error/exception";
	}
}
