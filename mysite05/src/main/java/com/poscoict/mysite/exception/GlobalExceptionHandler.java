package com.poscoict.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ControllerAdvice
public class GlobalExceptionHandler extends WebMvcConfigurerAdapter {
   private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);
  
   
   @ExceptionHandler(Exception.class)
   public String ExceptionHanlder(Model model,Exception e) {
      //1. 로깅
      StringWriter errors = new StringWriter();
      e.printStackTrace(new PrintWriter(errors));
      LOGGER.error(errors.toString());
      
      if(e instanceof NoHandlerFoundException) {
    	  return "error/404";
      }
      
      //2. 사과페이지(HTML 응답, 정상 종료)
      model.addAttribute("exception", errors.toString());
      return "error/exception";
   }
   
}