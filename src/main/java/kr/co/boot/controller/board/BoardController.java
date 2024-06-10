package kr.co.boot.controller.board;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.boot.dto.board.BoardDTO;
import kr.co.boot.service.board.BoardService;

@Controller
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardService service;
	
	@RequestMapping(value="/board/list.do")
	public String login(Model model, HttpSession session) {

		String page = "redirect:/";
		
		if(session.getAttribute("loginId") != null) {
			page = "board_list";			
			List<BoardDTO> list = service.list();
			model.addAttribute("list", list);
		}
				
		return page;
	}	
	
	
	@RequestMapping(value = "/board/write.go")
	public String writeForm(Model model, HttpSession session) {	
		logger.info("글쓰기 페이지 이동");
		
		String page = "redirect:/";
		
		if(session.getAttribute("loginId") != null) {
			page = "board_write";			
		}
		
		return page;
	}
	
	
	@RequestMapping(value = "/board/write.do")
	public String write(Model model, HttpSession session, 
			MultipartFile[] files, @RequestParam Map<String,String> params) {	
		
		String page = "redirect:/";
		logger.info("params : {}",params);
		
		if(session.getAttribute("loginId") != null) {
			page = service.write(files, params);			
		}
		
		return page;
	}
	
	
	@RequestMapping(value = "/board/detail.do")
	public String detail(Model model, HttpSession session, String idx) 
			throws IOException {							
		logger.info("detail : "+idx);
		//model 에 2개의 답을 넣어야 하므로 service 에서 넣도록 해야 한다.
		//model 은 request, response, session 과 함께 interface 이므로 객체화가 불가능 하다. 

		String page = "redirect:/";
		logger.info("loginId="+session.getAttribute("loginId"));
		if(session.getAttribute("loginId") != null) {
			page = "board_detail";
			service.detail(model, idx);				
		}
		
		return page;
	}
	
	
	@RequestMapping(value="/board/download.do")
	public ResponseEntity<Resource> download(String fileName) {
		logger.info("download fileName : "+fileName);
		return service.download(fileName);
	}
	
	@RequestMapping(value = "/board/delete.do")
	public String delete(Model model,String idx) {							
		logger.info("delete idx : {}",idx);
		service.delete(idx);
		return "redirect:/board/list.do";
	}

}
