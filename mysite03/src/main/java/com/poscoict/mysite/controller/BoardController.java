package com.poscoict.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(HttpSession session, 
			@RequestParam(value="p", defaultValue="1")Integer p, 
			@RequestParam(value="kwd", required = false, defaultValue = "") String kwd, 
			Model model) {
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		Map<String, Object> map = boardService.getContentsList(p, kwd);
		model.addAttribute("map", map);	
		model.addAttribute("authUser", authUser);	
		return "board/list";
	}
	
	
	
	@RequestMapping(value="/view")
	public String view(HttpSession session, Long no, Model model) {
		
		System.out.println(no);
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		BoardVo vo =  boardService.getContents(no);
		model.addAttribute("vo", vo);
		model.addAttribute("authUser", authUser);	
		model.addAttribute("no", no);
		return "board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session, Model model) {
		
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		model.addAttribute("a", "write");
		
		return "board/write";
	}
	
	
	
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public String reply(HttpSession session, Model model, Long no) {
		
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		
		model.addAttribute("a", "reply");
		model.addAttribute("no", no);
		
		return "board/write";
	}
	
	@RequestMapping(value="/write/{a}", method=RequestMethod.POST)
	public String write(HttpSession session, BoardVo vo, @PathVariable("a") String type, Long no) {
		
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		vo.setUserNo(authUser.getNo()); 
		
		if("reply".equals(type)) {
			boardService.addReply(vo, authUser.getNo(), no);
		}else {
			boardService.addContents(vo);
		}
		
		
		
		return "redirect:/board";
	}
	
	
	
	
	@RequestMapping(value="/modify")
	public String modify(HttpSession session, Long no, Model model) {
	
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		BoardVo vo =  boardService.getContents(no);
		model.addAttribute("vo", vo);
		model.addAttribute("authUser", authUser);	
		model.addAttribute("no", no);
		System.out.println(vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(Model model, BoardVo vo) {
		boardService.updateContents(vo);
		
		return "redirect:/board";
	}
	
	
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(HttpSession session, Long no) {
		
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board";
	}
	


}