package com.poscoict.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BorderDao;
import com.poscoict.mysite.vo.BorderVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String p = request.getParameter("p");  //몇번페이지눌러사나  // 1
		String kwd = request.getParameter("kwd"); // kwd값
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		List<BorderVo> list = null;
		
		int cPage;  //인트로바꾼거
		int cnt = 0;
		int pcnt = 0;  //페이지총개수
		
		if (p == null || p.length() == 0) {
	        cPage = 1;
	    }else {
	    	 cPage = Integer.parseInt(p);
	    }
		
		if(kwd == null || kwd.length()==0) {

			list =  new BorderDao().findAll((cPage - 1) * 5);
			cnt = new BorderDao().count();	//총개수
		}else {

			list =  new BorderDao().findAll((cPage - 1) * 5,kwd);
			cnt = new BorderDao().count(kwd);	//총개수
			
		}
		
	
		if(cnt % 5 == 0) {
			pcnt = cnt / 5;
		} else {
			pcnt  = (cnt / 5) + 1;
		}
		
		
		if(kwd == null || kwd.length()==0) {

		}else {

			if(cPage > pcnt) {
				cPage = 1;
			}
			
		}
		
		request.setAttribute("list", list);
		request.setAttribute("vo", authUser);
		request.setAttribute("pcnt", pcnt);
		request.setAttribute("p", Integer.valueOf(cPage));
		request.setAttribute("cnt", Integer.valueOf(cnt));
		request.setAttribute("kwd", kwd);
		
		MvcUtil.forward("board/list", request, response);
	}
}