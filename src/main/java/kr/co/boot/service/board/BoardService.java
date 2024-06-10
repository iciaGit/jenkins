package kr.co.boot.service.board;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.co.boot.dao.board.BoardDAO;
import kr.co.boot.dto.board.BoardDTO;
import kr.co.boot.dto.board.FileDTO;
import kr.co.boot.service.member.MemberService;

@Service
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardDAO dao;	
	@Value("${spring.servlet.multipart.location}") String root;
	
	public List<BoardDTO> list() {
		return dao.list();
	}

	
	public String write(MultipartFile[] files, Map<String, String> params) {
		//방금 게시글을 쓰고 나서 해당 게시글에 사진을 등록 할 때 photo 테이블에 데이터를 넣는 쿼리문 생성 해 보자
		
		//파일 이름 저장시 어떤 게시판에 소속되었는지도 넣어야 하는데...
		//그래서 게시판 입력 후 입력한 글의 idx 를 구해와야 한다.
		//이때 조건 1. 파라메터는 DTO 로 넣을 것
		BoardDTO dto = new BoardDTO();
		dto.setSubject(params.get("subject"));
		dto.setContent(params.get("content"));
		dto.setUser_name(params.get("user_name"));
		dao.writeBoard(dto);
		
		int idx = dto.getIdx();	//이러면 파라메터로 넣었던 dto 안에 idx 값이 저장되게 된다.	
		
		if(idx >0) {
			fileSave(idx,files);
		}		
		return "redirect:/board/detail.do?idx="+idx;
	}

	
	private void fileSave(int idx, MultipartFile[] files) {
		for (MultipartFile file : files) {			
			//업로드 파일이 없을 경우는 아래 내용을 진행할 이유가 없으므로...
			if(!file.getOriginalFilename().equals("")) {								
				String fileName = file.getOriginalFilename().toLowerCase();//1. 파일명 추출
				String ext = fileName.substring(fileName.lastIndexOf("."));
				String newFileName = UUID.randomUUID()+ext;//2. 새파일명 생성
				logger.info(fileName+"=>"+newFileName);													
				try {
					//3. 파일 저장
					byte[] bytes = file.getBytes();//MultipartFile  에서 바이너리 데이터를 추출
					Path filePath = Paths.get(root+"/"+newFileName);//저장할 경로를 지정
					Files.write(filePath,bytes);//파일 저장					
					logger.info(newFileName+" SAVE OK!");
					dao.fileWrite(fileName,newFileName,idx);					
				} catch (IOException e) {
					e.printStackTrace();
				}					
			}
		}		
	}


	public void detail(Model model, String idx) throws IOException {
		int success = dao.upHit(idx);//조회수 올리기
		logger.info("조회수 UP OK? "+success);		
		BoardDTO dto = dao.detail(idx);		//상세보기
		model.addAttribute("bbs", dto);
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>(); 
		
		for(FileDTO file : dao.fileList(idx)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ori_filename", file.getOri_filename());
			map.put("new_filename", file.getNew_filename());
			String type = Files.probeContentType(Paths.get(root+"/"+file.getNew_filename()));
			map.put("type", type.substring(0,type.indexOf("/")));
			list.add(map);
		}
		
		logger.info("list="+list);
				
		model.addAttribute("fileList", list);
		
	}


	public ResponseEntity<Resource> download(String fileName) {

		Resource resource = new FileSystemResource(root+"/"+fileName);
		HttpHeaders header = new HttpHeaders();
		try {
			header.add("content-type", "application/octet-stream");//content-type
			String oriFile = URLEncoder.encode(dao.getFileName(fileName), "UTF-8");			
			header.add("content-Disposition", "attachment;filename=\""+oriFile+"\"");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//보낼 내용, 헤더, 상태(200 또는 HttpStatus.OK 는 정상이라는 뜻)		
		return new ResponseEntity<Resource>(resource,header,HttpStatus.OK);
	}


	public void delete(String idx) {
		// dao.delete(idx);		
		//1. 해당 idx 로 사진이 있는지 먼저 확인
		List<FileDTO> fileList = dao.fileList(idx);
		logger.info("upload 한 사진 : "+fileList.size());		
		//2. 글을 먼저 지우고 (ONDELETE CASCADE 옵션으로 bbs 에서 지워지면 photo 도 삭제)
		if(dao.delete(idx)>0) {
			//3. 업로드 된 파일이 있다면... 파일을 지운다.
			for(FileDTO dto : fileList) {
				File file = new File(root+"/"+dto.getNew_filename());
				if(file.exists()) {
					logger.info(dto.getNew_filename()+" delete : "+file.delete());
				}
			}					
		}
		
		
	}

}
