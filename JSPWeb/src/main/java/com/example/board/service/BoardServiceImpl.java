package com.example.board.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.board.model.BoardDAO;
import com.example.board.model.BoardVO;

public class BoardServiceImpl implements BoardService {

	BoardDAO dao = BoardDAO.getInstance();

	@Override
	public void regist(HttpServletRequest request, HttpServletResponse response) {
		String writer =request.getParameter("writer");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		dao.regist(writer,title,content);

	}

	@Override
	public ArrayList<BoardVO> getList(HttpServletRequest request, HttpServletResponse response) {


		ArrayList<BoardVO> list = dao.getList();

		return list;
	}

	@Override
	public BoardVO getContent(HttpServletRequest request, HttpServletResponse response) {

		//a태그로 넘어오는 param
		String bno = request.getParameter("bno");
		
		dao.hitCount(bno);
		BoardVO vo = dao.getContent(bno);
		return vo;
	}


	@Override
	public void update(HttpServletRequest request, HttpServletResponse response) {

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String bno = request.getParameter("bno");


		dao.update(title, content, bno);
		
		

	}

	@Override
	public int delete(HttpServletRequest request, HttpServletResponse response) {
		
		int result = 0;
		
		String bno = request.getParameter("bno");
		
		result = dao.delete(bno);
		
		return result;
	}
	
	




}
