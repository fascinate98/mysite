package com.poscoict.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.service.FileUploadService;
import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private SiteService siteService;
   

   @RequestMapping("")
   public String main() {
      return "admin/main";
   }
   
   @RequestMapping("/board")
   public String board() {
      return "admin/board";
   }
   
   @RequestMapping("/guestbook")
   public String guestbook() {
      return "admin/guestbook";
   }
   
	
	
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(SiteVo vo, @RequestParam(value="upload-file") MultipartFile multipartFile) {
    	vo.setProfile(fileUploadService.restore(multipartFile));
       siteService.update(vo);
       
       return "redirect:/admin";
    }
	
}