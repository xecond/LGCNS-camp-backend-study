package board.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.dto.BoardInsertRequest;
import board.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RestController {
	@Autowired
	private BoardService boardService;
	
	// 목록 조회
	@GetMapping("/board")
	public ModelAndView openBoardList() throws Exception {
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	// 글쓰기 화면 요청을 처리하는 메서드
	@GetMapping("/board/write")
	public String openBoardWrite() throws Exception {
		return "/board/restBoardWrite";
	}
	
	// 글 저장 요청을 처리하는 메서드
	@PostMapping("/board/write")
	/*
	public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest request) throws Exception {
		boardService.insertBoard(boardDto, request);
		return "redirect:/board";
	}
	*/
	public String insertBoard(BoardInsertRequest boardInsertRequest, MultipartHttpServletRequest request) throws Exception {
		// 서비스 메서드에 맞춰서 데이터를 변경
		/*
		BoardDto boardDto = new BoardDto();
		boardDto.setTitle(boardInsertRequest.getTitle());
		boardDto.setContents(boardInsertRequest.getContents());
		*/
		BoardDto boardDto = new ModelMapper().map(boardInsertRequest, BoardDto.class);
		
		boardService.insertBoard(boardDto, request);
		return "redirect:/board";
	}
	
	// 상세 조회 요청을 처리하는 메서드
	@GetMapping("/board/{boardIdx}")
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
		
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		mv.addObject("board", boardDto);
		return mv;
	}
	
	// 수정 요청을 처리할 메서드
	@PutMapping("/board/{boardIdx}")
	public String updateBoard(@PathVariable("boardIdx") int boardIdx, BoardDto boardDto) throws Exception {
		boardDto.setBoardIdx(boardIdx);
		boardService.updateBoard(boardDto);
		return "redirect:/board";
	}
	
	// 삭제 요청을 처리할 메서드
	@DeleteMapping("/board/{boardIdx}")
	public String deleteBoard(@RequestParam("boardIdx") int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}
	
	// 파일 다운로드 요청을 처리하는 메서드
	@GetMapping("/board/file")
	public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {

		BoardFileDto boardFileDto = boardService.selectBoardFileInfo(idx, boardIdx);
		if (ObjectUtils.isEmpty(boardFileDto)) {
			return;
		}
		
		// 원본 파일 저장 위치에서 파일을 읽어서 호출(요청)한 곳으로 파일을 응답으로 전달
		Path path = Paths.get(boardFileDto.getStoredFilePath());
		byte[] file = Files.readAllBytes(path);
		
		response.setContentType("application/octet-stream"); // 브라우저가 해당 콘텐츠를 바이너리 파일로 처리하도록 지정
		
		response.setContentLength(file.length); // 다운로드할 파일의 크기를 명시
		response.setHeader("Content-Disposition",  // 브라우저가 파일을 다운로드하도록 지시
				"attachment; fileName=\"" + URLEncoder.encode(boardFileDto.getOriginalFileName(), "UTF-8") + "\";"); // 응답 본문을 바이너리로 전송하도록 지정
		
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(file);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
