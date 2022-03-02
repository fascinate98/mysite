package com.poscoict.mysite.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.mysite.vo.UserVo;

@RestController("guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;

	@PostMapping("")
	public Object add(@RequestBody GuestbookVo vo) {

		boolean result = guestbookService.addMessage(vo);
		return result ? JsonResult.success(vo) : JsonResult.fail("fail");
	}

	@GetMapping("/{startNo}")
	public Object read(@PathVariable(value = "startNo",  required= false) Long no) {
		List<GuestbookVo> list;
		list = no.equals(Long.valueOf(0))? guestbookService.findByNo(null) : guestbookService.findByNo(no);
		System.out.println("nsadfasdfo : "+ list);
		
		return JsonResult.success(list);
	}
	
	@DeleteMapping("")
	public Object delete(@RequestBody GuestbookVo vo) {
		System.out.println("no : "+ vo.getNo());
		boolean result = guestbookService.deleteMessage(vo);
		
		Long data = result? vo.getNo() : -1L;
		// 삭 성공
		



		// 삭 실패


		return JsonResult.success(data);
	}

//	@DeleteMapping("/delete/{no}")
//	public Object delete(@PathVariable("no") Long no,
//			@RequestParam(value = "password", required = true, defaultValue = "") String password) {
//		System.out.println("no : " + no);
//		System.out.println("password : " + password);
//		
//		//boolean result = guestbookService.deleteMessage(no, password);
//
//		Long data = 0L;
//		// 삭 성공
//		
//		data = result? no : -1L;
//
//
//		// 삭 실패
//
//
//		return JsonResult.success(data);
//	}
	
	
}
