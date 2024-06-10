package kr.co.boot.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.boot.dto.member.MemberDTO;
import kr.co.boot.service.member.MemberService;

@Controller
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired MemberService service;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {		
		return "member_login";
	}
		
	@RequestMapping(value = "/join.go", method = RequestMethod.GET)
	public String home() {
		return "member_join";
	}
	
	
	@GetMapping(value="/overlay.ajax")
	@ResponseBody
	public Map<String, Object>overlay(String id){		
		Map<String, Object> map = new HashMap<String, Object>();		
		int cnt = service.overlay(id);
		map.put("cnt", cnt);
		return map;
	}
	
	
	@PostMapping(value="/join.do")
	public String join(MemberDTO dto, Model model) {
		logger.info("ID:{}",dto.getId());
		logger.info("E-MAIL:{}",dto.getEmail());		
		return service.join(dto, model);
	}
	
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(HttpServletRequest req, 
			@RequestParam Map<String, String> params) {		
		
		HttpSession session = req.getSession();
		//IPV4 를 하려면 run as > Run Configuration > Arguments > VM
		// -Djava.net.preferIPv4Stack=true
		params.put("ip", req.getRemoteAddr());
		logger.info("params : {}",params);
		String page = "redirect:/";
		
		if(service.login(params)) {
			session.setAttribute("loginId", params.get("id"));
			session.setAttribute("perm", params.get("mode"));
			if(params.get("mode").equals("admin")) {
				page = "redirect:/member/list.do";
			}else {
				page = "redirect:/board/list.do";
			}
		}
				
		return page;
	}
	
	
	@RequestMapping(value="/member/list.do")
	public String home(Model model, HttpSession session
			,@RequestParam Map<String,String> params) {		
		
		String page = "member_login";
		if(!session.getAttribute("perm").equals("admin")) {
			model.addAttribute("msg", "해당 권한이 없습니다.");
		}else {
			logger.info("params : {}",params);
			List<MemberDTO> list = service.list(params);
			model.addAttribute("list", list);
			page = "member_list";
		}
		return page;
	}
		
	
	@GetMapping(value="/member/detail.do")
	public String detail(String id, Model model, HttpSession session) {
		
		String page = "member_login";
		if(!session.getAttribute("perm").equals("admin")) {
			model.addAttribute("msg", "해당 권한이 없습니다.");
		}else {
			logger.info("detail id="+id);
			service.detail(id, model);
			page = "member_detail";
		}
		return page;
	}
	
	
	@PostMapping(value="/member/update.ajax")
	@ResponseBody
	public Map<String, Object> update(MemberDTO dto) {	
		logger.info("dto : "+dto.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", service.update(dto));
		return map;
	}
}
