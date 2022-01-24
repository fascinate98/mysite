package com.poscoict.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BorderDao;
import com.poscoict.mysite.vo.BorderVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String no = request.getParameter("no");

		
		boolean b = new BorderDao().update(Long.parseLong(no), title, content);
		request.setAttribute("p", Integer.valueOf(1));
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);	
	}

}
