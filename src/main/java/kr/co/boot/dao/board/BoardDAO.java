package kr.co.boot.dao.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.boot.dto.board.BoardDTO;
import kr.co.boot.dto.board.FileDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> list();

	int writeBoard(BoardDTO dto);

	int fileWrite(String fileName, String newFileName, int idx);

	int upHit(String idx);

	BoardDTO detail(String idx);

	List<FileDTO> fileList(String idx);

	String getFileName(String fileName);

	int delete(String idx);

}
