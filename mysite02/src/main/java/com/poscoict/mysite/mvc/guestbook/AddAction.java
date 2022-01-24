package com.poscoict.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		String content = request.getParameter("content");
		
		request.setCharacterEncoding("utf-8");
		
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(pass);
		vo.setMessage(content);
		System.out.println("dfdf");
		
		new GuestbookDao().insert(vo);
		MvcUtil.redirect(request.getContextPath() + "/guestbook", request, response);	
	}

}
