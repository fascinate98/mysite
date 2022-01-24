package com.poscoict.mysite.web.mvc.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poscoict.mysite.dao.BorderDao;
import com.poscoict.mysite.vo.BorderVo;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ViewAction implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String no = request.getParameter("no");
		int visitCount = 0;
		BorderVo vo =  new BorderDao().view(Long.parseLong(no));
		request.setAttribute("vo", vo);
		
		
		//쿠키읽기
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for(Cookie cookie : cookies) {
				if(vo.getNo().toString().equals(cookie.getName())) {
					visitCount = Integer.parseInt(cookie.getValue());
					break;
				}
			}
		}
		
		
		visitCount++;
		//쿠키쓰기(굽기)
		Cookie cookie =  new Cookie(vo.getNo() + "", String.valueOf(visitCount));
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(60); //1day
		response.addCookie(cookie);
		
		if(visitCount <= 1) {
			new BorderDao().hit(Long.parseLong(no));
		}
		
		request.setAttribute("p", Integer.valueOf(1));
		MvcUtil.forward("/board/view", request, response);	
		
	}

}
