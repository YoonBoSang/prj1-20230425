package com.example.demo.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import software.amazon.awssdk.core.sync.*;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class BoardService {

	@Autowired
	private S3Client s3;
	
	@Autowired
	private BoardLikeMapper likeMapper;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Autowired
	private BoardMapper mapper;

	public List<Board> listBoard() {
		List<Board> list = mapper.selectAll();
		return list;
	}

	public Board getBoard(Integer id) {
		return mapper.selectById(id);
	}

	public boolean modify(Board board, List<String> removeFileNames, MultipartFile[] Files) throws Exception {

		// FileName 테이블 삭제
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {
				String objectKey = "board/" + board.getId() + "/" + fileName;
				
				DeleteObjectRequest dor = DeleteObjectRequest.builder()
						.key(objectKey)
						.bucket(bucketName)
						.build();
				s3.deleteObject(dor);
				// 테이블에서 삭제
				mapper.deleteFileNamesByBoardIdANDFileName(board.getId(), fileName);
			}
		}

		// 새 파일 추가
		for (MultipartFile file : Files) {
			if (file.getSize() > 0) {
				String objectKey = "board/" + board.getId() + "/" + file.getOriginalFilename();
				PutObjectRequest por = PutObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.build();
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				s3.putObject(por, rb);

				mapper.insertFileName(board.getId(), file.getOriginalFilename());

			}
		}

		// 게시물(Board) 테이블 수정
		int cnt = mapper.update(board);

		return cnt == 1;
	}

	public boolean remove(Integer id) {
		// 파일명 조회
		List<String> fileNames = mapper.selectFileNamesByBoardId(id);

		// FileNames 테이블의 데이터 지우기
		mapper.deleteFileNamesByBoardId(id);

		// 하드디스크의 파일 지우기
		for (String fileName : fileNames) {
			String objectKey = "board/" + id + "/" + fileName;
			DeleteObjectRequest dor = DeleteObjectRequest.builder()
					.bucket(bucketName)
					.key(objectKey)
					.build();
			s3.deleteObject(dor);
		}

		// 게시물 테이블의 데이터 지우기
		int cnt = mapper.deleteById(id);

		return cnt == 1;
	}

	public boolean add(Board board, MultipartFile[] files) throws Exception {

		// 게시물 insert
		int cnt = mapper.add(board);

		for (MultipartFile file : files) {
			if (file.getSize() > 0) {
				String objectKey = "board/" + board.getId() + "/" + file.getOriginalFilename();

				PutObjectRequest por = PutObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.build();
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				s3.putObject(por, rb);

				mapper.insertFileName(board.getId(), file.getOriginalFilename());

			}
		}

		return cnt == 1;
	}

	public Map<String, Object> listBoard(Integer page, String search, String type) {
		Integer rowPerPage = 10;
		Integer numOfRecords = 0;

		// 쿼리 LIMIT 절에 사용할 시작 인덱스
		Integer startIndex = (page - 1) * rowPerPage;

		// 페이지네이션이 필요한 정보
		// 전체 레코드 수
		numOfRecords = mapper.countAll(search, type);

		// 마지막 페이지 번호
		Integer lastPageNumber = (numOfRecords - 1) / rowPerPage + 1;

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
		List<Board> list = mapper.selectAllPaging(startIndex, rowPerPage, search, type);

		//
		return Map.of("pageInfo", pageInfo, "boardList", list);
	}

	public void removeByWriter(String writer) {
		List<Integer> idList = mapper.selectIdByWriter(writer);
		
		for (Integer id : idList) {
			remove(id);
		}
	}

	public Map<String, Object> like(Like like, Authentication authentication) {
		Map<String,Object> result = new HashMap<>();
		
		result.put("like", false);
		
		like.setMemberId(authentication.getName());
		Integer deleteCnt = likeMapper.delete(like);
		
		if (deleteCnt != 1){
			Integer insertCnt = likeMapper.insert(like);
			result.put("like", true);
		}
		
		return result;
	}

}
