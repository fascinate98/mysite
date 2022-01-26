package com.poscoict.mysite.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.security.AuthUser;
import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@Auth
	@RequestMapping("")
	public String index(@AuthUser UserVo authUser,
			@RequestParam(value="p", defaultValue="1")Integer p, 
			@RequestParam(value="kwd", required = false, defaultValue = "") String kwd, 
			Model model) {
		
		Map<String, Object> map = boardService.getContentsList(p, kwd);
		model.addAttribute("map", map);	
		model.addAttribute("authUser", authUser);	
		return "board/list";
	}
	
	@Auth
	@RequestMapping(value="/view")
	public String view(@AuthUser UserVo authUser,  Long no, Model model) {
		
		System.out.println(no);
		
		BoardVo vo =  boardService.getContents(no);
		model.addAttribute("vo", vo);
		model.addAttribute("authUser", authUser);	
		model.addAttribute("no", no);
		
		System.out.println(vo.getUserNo());
		System.out.println(authUser.getNo());
		
		return "board/view";
	}
	
	
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write( Model model) {
		
		model.addAttribute("a", "write");
		
		return "board/write";
	}
	
	
	
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public String reply(@AuthUser UserVo authUser, Model model, Long no) {
		
		/* access controller */
		if(authUser == null) {
			return "redirect:/board";
		}
		
		model.addAttribute("a", "reply");
		model.addAttribute("no", no);
		
		return "board/write";
	}

	@RequestMapping(value="/write/{a}", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo vo, @PathVariable("a") String type, Long no) {
		
		/* access controller */
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
	public String modify(@AuthUser UserVo authUser, Long no, Model model) {
	
	
		
		BoardVo vo =  boardService.getContents(no);
		model.addAttribute("vo", vo);
		model.addAttribute("authUser", authUser);	
		model.addAttribute("no", no);
		System.out.println(vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(Model model, BoardVo vo, Long no) {
		vo.setNo(no);
		boardService.updateContents(vo);
		System.out.println("2" + vo);
		return "redirect:/board";
	}
	
	
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(@AuthUser UserVo authUser, Long no) {
		
		/* access controller */

		if(authUser == null) {
			return "redirect:/board";
		}
		
		boardService.deleteContents(no);
		return "redirect:/board";
	}
	


}