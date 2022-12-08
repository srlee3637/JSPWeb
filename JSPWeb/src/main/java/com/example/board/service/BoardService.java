package com.example.board.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.board.model.BoardVO;

public interface BoardService {
	
	public void regist(HttpServletRequest request, HttpServletResponse response);
	public ArrayList<BoardVO> getList(HttpServletRequest request, HttpServletResponse response);
	public BoardVO getContent(HttpServletRequest request, HttpServletResponse response);
	public void update(HttpServletRequest request, HttpServletResponse response);
	public int delete(HttpServletRequest request, HttpServletResponse response);
}
 