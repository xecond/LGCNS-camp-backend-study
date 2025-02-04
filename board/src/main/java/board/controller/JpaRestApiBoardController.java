package board.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import board.dto.BoardListResponse;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.service.JpaBoardService;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v2")
//@CrossOrigin(origins = "http://localhost:5173")
public class JpaRestApiBoardController {
	@Autowired
	private JpaBoardService boardService;
	
	// 목록 조회
	@GetMapping("/board")
	public ResponseEntity<Object> openBoardList() throws Exception {
	
		List<BoardListResponse> results = new ArrayList<>();
		
		try {
			List<BoardEntity> boardList = boardService.selectBoardList();
			boardList.forEach(dto -> results.add(new ModelMapper().map(dto, BoardListResponse.class)));
			return new ResponseEntity<>(results, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("목록 조회 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 글 저장 요청을 처리하는 메서드
	@PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> insertBoard(@RequestParam("board") String boardData, MultipartHttpServletRequest request) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		BoardEntity boardEntity = objectMapper.readValue(boardData, BoardEntity.class);
		Map<String, String> result = new HashMap<>();
		
		try {
			boardService.insertBoard(boardEntity, request);
			result.put("message", "게시판 저장 성공");
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch(Exception e) {
			result.put("message", "게시판 저장 실패");
			result.put("description", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}
	
	// 상세 조회 요청을 처리하는 메서드
	@GetMapping("/board/{boardIdx}")
	public ResponseEntity<Object> openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		BoardEntity boardEntity = boardService.selectBoardDetail(boardIdx);
		if (boardEntity == null) {
			Map<String, Object> result = new HashMap<>();
			result.put("code", HttpStatus.NOT_FOUND.value());
			result.put("name", HttpStatus.NOT_FOUND.name());
			result.put("message", "게시판 번호 " + boardIdx + "와 일치하는 게시물이 존재하지 않습니다.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(boardEntity);
		}
	}
	
	// 수정 요청을 처리할 메서드
	@PutMapping("/board/{boardIdx}")
	public void updateBoard(@PathVariable("boardIdx") int boardIdx, @RequestBody BoardEntity boardEntity) throws Exception {
		boardEntity.setBoardIdx(boardIdx);
		boardService.updateBoard(boardEntity);
	}
	
	// 삭제 요청을 처리할 메서드
	@DeleteMapping("/board/{boardIdx}")
	public void deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
	}
	
	// 파일 다운로드 요청을 처리하는 메서드
	@GetMapping("/board/file")
	public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {

		BoardFileEntity boardFileEntity = boardService.selectBoardFileInfo(idx, boardIdx);
		if (ObjectUtils.isEmpty(boardFileEntity)) {
			return;
		}
		
		// 원본 파일 저장 위치에서 파일을 읽어서 호출(요청)한 곳으로 파일을 응답으로 전달
		Path path = Paths.get(boardFileEntity.getStoredFilePath());
		byte[] file = Files.readAllBytes(path);
		
		response.setContentType("application/octet-stream");
		response.setContentLength(file.length); 
		response.setHeader("Content-Disposition",
				"attachment; fileName=\"" + URLEncoder.encode(boardFileEntity.getOriginalFileName(), "UTF-8") + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(file);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
