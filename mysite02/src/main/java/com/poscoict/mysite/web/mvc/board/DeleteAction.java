package com.poscoict.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BorderDao;
import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.vo.BorderVo;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String no = request.getParameter("no");

		
		BorderVo vo = new BorderVo();
		vo.setNo(Long.parseLong(no));
		
		System.out.println("no:" + no);
		
		new BorderDao().delete(vo);
		request.setAttribute("p", Integer.valueOf(1));
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);	
	}

}
