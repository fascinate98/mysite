package com.poscoict.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.UserRepository;
import com.poscoict.mysite.vo.UserVo;

@Service
public class UserService {
   @Autowired
   private UserRepository userRepository;
   
   public boolean join(UserVo vo) {
	   boolean result =  userRepository.insert(vo);
	   return result;
   }

	public UserVo getUser(String email, String password) {
		   return userRepository.findByEmailAndPassword(email, password);
	}

	public UserVo getUser(Long userNo) {
		
		return userRepository.findByNo(userNo);
	}

	public void updateUser(UserVo userVo) {
		
		userRepository.update(userVo);
	}
}