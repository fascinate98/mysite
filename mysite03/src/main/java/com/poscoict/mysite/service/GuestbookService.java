package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poscoict.mysite.repository.GuestbookRepository;
import com.poscoict.mysite.vo.GuestbookVo;


@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	
	public List<GuestbookVo> getMessgaeList(){

		return guestbookRepository.findAll();
	}
	
	public Boolean deleteMessage(GuestbookVo vo) {
		
		return guestbookRepository.delete(vo);
	}
	
	public Boolean addMessage(GuestbookVo vo) {

		return guestbookRepository.insert(vo);
	}
	
	

}
