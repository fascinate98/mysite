package com.poscoict.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BorderDao;
import com.poscoict.mysite.vo.BorderVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String no = request.getParameter("no");
		
		BorderVo vo =  new BorderDao().view(Long.parseLong(no));
		request.setAttribute("vo", vo);
		
		MvcUtil.forward("/board/modify", request, response);	
	}

}
