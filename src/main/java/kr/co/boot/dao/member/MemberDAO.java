package kr.co.boot.dao.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.boot.dto.member.MemberDTO;

@Mapper
public interface MemberDAO {

	List<MemberDTO> list(Map<String, String> params);

	int join(MemberDTO dto);

	MemberDTO detail(String id);

	int update(MemberDTO dto);

	int login(String id, String pw);

	int overlay(String id);

}
