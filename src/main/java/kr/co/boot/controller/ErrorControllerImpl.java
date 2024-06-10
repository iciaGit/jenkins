package kr.co.boot.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {
	
	@RequestMapping(value="/error")
	public String error(Model model, HttpServletRequest req) {
		
		int code = (int) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		String msg = "<h2>"+code+" ERROR </h2>";
		
		 if(code == 400) {
			msg += "<h3>입력값이 맞지 않습니다.</h3>";
		}else if(code == 403) {
			msg += "<h3>권한이 없습니다.</h3>";
		}else if(code == 404) {
			msg += "<h3>찾으시는 페이지 또는 요청이 존재하지 않습니다.</h3>";
		}else if(code == 405) {
			msg += "<h3>약속된 메서드를 사용 하세요</h3>";
		}else if(code == 500) {
			msg += "<h3>서버에서 처리중 문제가 발생 했습니다.</h3>";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("link", "./");

		
		return "error";
	}

}
