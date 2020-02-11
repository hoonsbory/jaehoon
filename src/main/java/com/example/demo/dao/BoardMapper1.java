package com.example.demo.dao;

import java.util.List;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.bean.Board3;

@Mapper
public interface BoardMapper1 {

	
	
	int addBoard3(Board3 board);

	int getSeq3();

	List<Board3> boardList3();

	int countUp3(@Param("board_num") int board_num);

	int updateBoard33(Board3 board);

	int deleteBoard3(@Param("board_num") int board_num);

	Board3 boardView3(@Param("board_num") int board_num);

}
