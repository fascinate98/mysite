package com.poscoict.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BorderDao;
import com.poscoict.mysite.vo.BorderVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		//원글 글번호
		String no = request.getParameter("no");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		

		HttpSession session = request.getSession(); 
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		BorderVo vo =  new BorderDao().findByNo(Long.parseLong(no));
		
		System.out.println(no);
		
		new BorderDao().updateboard(vo.getReplyCnt() + vo.getOrderNo(), vo.getGroupNo());
		//insert(String title, String contents, Long no, int g_no, int o_no, int depth) 
		
		if(vo.getDepth()+1 >= 3) {
			System.out.println("dfdf" );
			for(int i = 2; i < vo.getDepth() + 1; i++) {
				
				System.out.println("dsfsf" + vo.getDepth());
				new BorderDao().increasereplycnt1(vo.getDepth() + 1 -i, vo.getGroupNo());
			}
		
		} 
		new BorderDao().insert(title, content, authUser.getNo(), vo.getGroupNo(), vo.getReplyCnt() + vo.getOrderNo() + 1, vo.getDepth()+1 );
		new BorderDao().increasereplycnt(Long.parseLong(no));

		request.setAttribute("p", Integer.valueOf(1));
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);	
	}

}
