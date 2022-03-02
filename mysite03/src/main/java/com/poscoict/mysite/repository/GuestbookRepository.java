package com.poscoict.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public boolean delete(GuestbookVo vo) {
		 int count =sqlSession.delete("guestbook.delete", vo);	
		return count==1;
	}
	
	public boolean insert(GuestbookVo vo) {
		 int count =sqlSession.insert("guestbook.insert", vo);
		return  count==1;
	}
	
	public List<GuestbookVo> findByNo(Long no) {
		return sqlSession.selectList("guestbook.findByNo", no);
	}
}