package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	public List<Board> listBoard() {
		List<Board> list = mapper.selectAll();
		return list;
	}
	
	public Board getBoard(Integer id) {
		return mapper.selectById(id);
	}

	public boolean modify(Board board) {
		int cnt = mapper.update(board);
		
		return cnt == 1;
	}

	public boolean remove(Integer id) {
		int cnt = mapper.deleteById(id);
		return cnt == 1;
	}

	public boolean add(Board board) {
		int cnt = mapper.add(board);
		return cnt == 1;
	}

	public Map<String, Object> listBoard(Integer page, String search) {
		Integer rowPerPage = 10;
		Integer numOfRecords = 0;
		
		// 쿼리 LIMIT 절에 사용할 시작 인덱스
		Integer startIndex = (page - 1) * rowPerPage;
		
		// 페이지네이션이 필요한 정보
		// 전체 레코드 수
			numOfRecords = mapper.countAll(search);			

		// 마지막 페이지 번호
		Integer lastPageNumber = (numOfRecords - 1) / rowPerPage  + 1;
		
		// 페이지네이션 왼쪽번호
		Integer leftPageNum = page - 5;
		Integer rightPageNum = page + 4;
		leftPageNum = Math.max(leftPageNum, 1);
		
		// 페이지네이션 오른쪽번호
		
		rightPageNum = Math.min(lastPageNumber, rightPageNum);
		
		
		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("rightPageNum", rightPageNum);
		pageInfo.put("leftPageNum", leftPageNum);
		pageInfo.put("lastPageNum", lastPageNumber);
		pageInfo.put("currentPageNum", page);
		
		// 게시물 목록
		List<Board> list = mapper.selectAllPaging(startIndex, rowPerPage, search);
		
		//
		return Map.of("pageInfo", pageInfo, "boardList", list);
	}

}
