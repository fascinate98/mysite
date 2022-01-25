package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAll(int i) {
		Map<String, Object> map = new HashMap<>();
		map.put("i", i );

		List<BoardVo> vo = sqlSession.selectList("board.findAll", map);

		return sqlSession.selectList("board.findAll", map);
	}
	
	public List<BoardVo> findAll(int i, String kwd) {
		Map<String, Object> map = new HashMap<>();
		map.put("i", i );
		map.put("kwd", kwd);
		

		return sqlSession.selectList("board.findAll", map);
	}
	

	public boolean insert(String title, String contents, Long no) {
		Map<String, Object> map = new HashMap<>();
		map.put("title", title);
		map.put("contents", contents);
		map.put("no", no);
		
		int count = sqlSession.insert("board.insert", map);
		return count == 1;
	}
	
	
	public boolean insert(String title, String contents, Long no, int g_no, int o_no, int depth) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("title", title);
		map.put("contents", contents);
		map.put("no", no);
		map.put("g_no", g_no);
		map.put("o_no", o_no);
		map.put("depth", depth);
		map.put("no", no);
		
		int count = sqlSession.insert("board.insert", map);
		return count == 1;

	}
	
	
	
	public boolean updateboard(int o_no, int g_no) {
		Map<String, Object> map = new HashMap<>();
		map.put("o_no", o_no);
		map.put("g_no", g_no);
		
		int count = sqlSession.update("board.updateboard", map);
		return count == 1;
	}
	
	
	public boolean increasereplycnt(Long no) {

		int count = sqlSession.update("board.increasereplycnt", no);
		return count == 1;
	}
	
	
	public boolean hit(Long no) {
		int count = sqlSession.selectOne("board.hit", no);
		return count == 1;
	}
	
	
	
	public boolean increasereplycnt1(int depth, int g_no) {
		Map<String, Object> map = new HashMap<>();
		map.put("depth", depth);
		map.put("g_no", g_no);
		
		int count = sqlSession.update("board.increasereplycnt1", map);
		return count == 1;
	}
	
	
	public boolean update(Long no, String title, String contents) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("title", title);
		map.put("contents", contents);
		
		int count = sqlSession.update("board.update", map);
		return count == 1;
	}	
	
	
	public BoardVo view(Long no) {
		return sqlSession.selectOne("board.view", no);
	}
	
	
	
	public BoardVo findByNo(Long no) {
	    return sqlSession.selectOne("board.findByNo", no);
	}	
	

	
	public boolean delete(Long no) {
		int count = sqlSession.update("board.delete", no);
		return count == 1;
	
	}
	

	public int count() {
		return sqlSession.selectOne("board.count");
	}	
	
	
	public int getTotalCount(String kwd) {
		return sqlSession.selectOne("board.getTotalCount", kwd);
	}	



}
