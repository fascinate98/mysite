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
	private DataSource datasource;
	
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public int delete(GuestbookVo vo) {
		return sqlSession.delete("guestbook.delete", vo);	
	}
	
	public int insert(GuestbookVo vo) {
		
		return sqlSession.insert("guestbook.insert", vo);
	}
	

}