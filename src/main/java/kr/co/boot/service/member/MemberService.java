package kr.co.boot.service.member;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.boot.dao.member.MemberDAO;
import kr.co.boot.dto.member.MemberDTO;
import kr.co.boot.service.board.BoardService;

@Service
@PropertySource("classpath:admin.properties")
public class MemberService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired MemberDAO dao;
	
	@Value("${user.id}") private String user_id;	
	@Value("${user.pw}") private String user_pw;	
	@Value("${user.ip}") private String user_ip;

	public String join(MemberDTO dto, Model model) {
		String page = "member_join";
		
		if(dao.join(dto)>0) {		
			page = "redirect:/";
		}else {
			model.addAttribute("msg", "가입에 실패 했습니다.");
		}				
		return page;
	}
	
	public boolean login(Map<String, String> params) {
		
		boolean success = false;
		String id = params.get("id");
		String pw = params.get("pw");
		String ip = params.get("ip");
		String mode = params.get("mode");

		if(mode.equals("admin")) {
			logger.info("user_id : "+user_id);
			logger.info("user_pw : "+user_pw);
			logger.info("user_ip : "+user_ip);
			if(user_id.equals(id) && user_pw.equals(pw) && user_ip.equals(ip)) {
				success = true;
			}
		}else {
			success = dao.login(id,pw) > 0;
		}
		
		return success;
	}
	
	public List<MemberDTO> list(Map<String, String> params) {
		return dao.list(params);
	}

	public void detail(String id, Model model) {		
		MemberDTO dto = dao.detail(id);
		model.addAttribute("info", dto);
	}

	public int update(MemberDTO dto) {		
		return dao.update(dto);
	}

	public int overlay(String id) {
		return dao.overlay(id);
	}



}
