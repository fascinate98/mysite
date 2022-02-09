package com.poscoict.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscoict.mysite.repository.GuestbookRepository;
import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;

	@RequestMapping("")
	public String index(Model model) {
		List<GuestbookVo> list =  guestbookService.getMessgaeList();
		model.addAttribute("list", list);	
		return "guestbook/index";
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(GuestbookVo vo) {
		System.out.println("guestbookvo : "+ vo);
		guestbookService.addMessage(vo);
		System.out.println("guestbookvo : "+ vo);
		return "redirect:/guestbook";  //emaillist03안적음 기술이니까
	}
	
	//delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET) //기본값 : value , method
	public String delete(Integer no,  Model model) {
		model.addAttribute("no", no);
		return "/guestbook/delete";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST) //기본값 : value , method
	//@GetMapping 이랑 동일
	public String delete(GuestbookVo vo){

		guestbookService.deleteMessage(vo);
		return "redirect:/guestbook"; 
	}
	
}