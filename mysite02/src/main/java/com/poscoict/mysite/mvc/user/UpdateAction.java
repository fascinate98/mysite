package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.UserDao;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");

		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");

		new UserDao().update(authUser.getNo(), name, password, gender);
		
		if(!password.isBlank()) {
			authUser.setPassword(password);
		}

		authUser.setName(name);
		authUser.setGender(gender);
		
	
		//인증 처리 (세션 처리)
		session.setAttribute("authUser", authUser);
		
		MvcUtil.redirect(request.getContextPath() , request, response);	

	   }

}
