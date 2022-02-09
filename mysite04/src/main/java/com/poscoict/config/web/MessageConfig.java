package com.poscoict.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("com/postcoict/mysite/config/web/messages/messages_ko");
		messageSource.setDefaultEncoding("utf-8");
		
		return messageSource();
	}
}
