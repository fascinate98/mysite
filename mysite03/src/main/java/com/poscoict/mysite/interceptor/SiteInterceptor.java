package com.poscoict.mysite.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.SiteVo;

public class SiteInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private ServletContext context;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		SiteVo sitevo = (SiteVo)context.getAttribute("siteVo");
		sitevo = siteService.getSite(sitevo);
		context.setAttribute("siteVo", sitevo);  
		
		return true;
	}
}