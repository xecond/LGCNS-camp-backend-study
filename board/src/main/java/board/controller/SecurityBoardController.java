package board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityBoardController {
	@GetMapping("/board")
	public String board() {
		return "/board/index";
	}
}