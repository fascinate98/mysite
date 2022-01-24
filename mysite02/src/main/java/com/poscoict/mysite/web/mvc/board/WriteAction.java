package com.poscoict.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BorderDao;
import com.poscoict.mysite.vo.BorderVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String no = request.getParameter("no");

		BorderVo vo = new BorderVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserNo(Long.parseLong(no));
		
		boolean b = new BorderDao().insert(vo.getTitle(), vo.getContents(), Long.parseLong(no));
		System.out.println(b);
		request.setAttribute("p", Integer.valueOf(1));
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);	
	}

}
