package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.poscoict.mysite.exception.UserRepositoryException;
import com.poscoict.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public boolean update(UserVo vo) throws UserRepositoryException {
	      
	      int count = sqlSession.update("user.update", vo);
	       return count == 1;
	   }

	public UserVo findByEmailAndPassword(String email, String password) throws UserRepositoryException{
		StopWatch sw = new StopWatch();
		sw.start();
		
		Map<String, String> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);
		UserVo vo =  sqlSession.selectOne("user.findByEmailAndPassword", map);
		
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		System.out.println(totalTime);
		return vo;
	   }
	
	
	public boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return count == 1;
	}



	public UserVo findByNo(Long no) {
	    return sqlSession.selectOne("user.findByNo", no);
	}	
	public UserVo findByEmail(String email) {
		return sqlSession.selectOne("user.findByEmail", email);
	}	
}
