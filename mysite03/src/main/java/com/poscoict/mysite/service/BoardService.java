package com.poscoict.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.BoardRepository;
import com.poscoict.mysite.vo.BoardVo;

import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.GuestbookVo;

@Service
public class BoardService {
	
	private static final int LIST_SIZE = 5; //리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; //페이지 리스트의 페이지 수
	
	
	
	@Autowired
	private BoardRepository boardRepository;
	
	//새글 답글 달기
	public boolean addContents(BoardVo vo){
		return boardRepository.insert(vo.getTitle(), vo.getContents(), vo.getUserNo());
	}
	
	
	
	
	public boolean addReply(BoardVo vo, Long myno, Long pno){

		BoardVo pvo =  boardRepository.findByNo(pno);
		System.out.println(pno);
		
		boardRepository.updateboard(pvo.getReplyCnt() + pvo.getOrderNo(), pvo.getGroupNo());
		//insert(String title, String contents, Long no, int g_no, int o_no, int depth) 
		
		if(pvo.getDepth()+1 >= 3) {
			System.out.println("dfdf" );
			for(int i = 2; i < pvo.getDepth() + 1; i++) {
				boardRepository.increasereplycnt1(pvo.getDepth() + 1 -i, pvo.getGroupNo());
			}
		
		}
		
		boardRepository.insert(vo.getTitle(), vo.getContents(), myno, pvo.getGroupNo(), pvo.getReplyCnt() + pvo.getOrderNo() + 1, pvo.getDepth()+1 );
		return boardRepository.increasereplycnt(pno);
	}
	
	
	
	
	//veiw
	public BoardVo getContents(Long no){
		
	
		return boardRepository.view(no);
	}
	
	//글 수정하기전
	public BoardVo getContents(Long no, Long userNo) {
		return boardRepository.view(no);
	}
	
	//글 수정
	public Boolean updateContents(BoardVo vo) {		
		return boardRepository.update(vo.getNo(), vo.getTitle(), vo.getContents());
	}
	
	//글 삭
	public Boolean deleteContents(Long no, Long userNo) {
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		return boardRepository.delete(vo);
	}
	
	//글 리스트(찾기결과)
	public Map<String, Object> getContentsList(Integer p, String kwd) {
		Map<String, Object> map = new HashMap<>();
		List<BoardVo> list = null;
		
		int cPage;  //인트로바꾼거
		int cnt = 0;
		int pcnt = 0;  //페이지총개수
		
		if (p == null) {
	        cPage = 1;
	    }else {
	    	 cPage = p;
	    }
		
		if(kwd == null ) {

			list =  boardRepository.findAll((cPage - 1) * LIST_SIZE);
			cnt = boardRepository.count();	//총개수
		}else {

			list =  boardRepository.findAll((cPage - 1) * LIST_SIZE ,kwd);
			cnt = boardRepository.count(kwd);	//총개수
			
		}
		
	
		if(cnt % 5 == 0) {
			pcnt = cnt / 5;
		} else {
			pcnt  = (cnt / 5) + 1;
		}
		
		
		if(kwd == null || kwd.length()==0) {

		}else {

			if(cPage > pcnt) {
				cPage = 1;
			}
			
		}
		
		map.put("list", list);
		map.put("p", cPage);
		map.put("pcnt", pcnt);
		map.put("cnt", cnt);
		map.put("kwd", kwd);
		
		
		return map;
	}


}
