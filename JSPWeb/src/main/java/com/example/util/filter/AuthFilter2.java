 package com.example.util.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthFilter2
 */
@WebFilter({"/board/board_modify.board",
	"/board/updateForm.board",
"/board/board_delete.board"})//경로 수정화면, 수정기능, 삭제기능
public class AuthFilter2 implements Filter {


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		/**
		 * 세션 user_id와 request로 넘어오는 작성자가 다르면 수정 불가
		 * 
		 * 1.각 요청 경로에서 writer가 파라미터로 반드시 전달되도록 처리.
		 * 
		 */
		request.setCharacterEncoding("utf-8");

		//권한 검사
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

		//각 요청에 넘어오는 writer파라미터
		String writer = request.getParameter("writer");

		//세션에저장된 user_id
		HttpSession session = req.getSession();
		String user_id = (String)session.getAttribute("user_id");

		System.out.println("작성자:" + writer);
		System.out.println("세션ID:" + user_id);

		//세션이 없거나 or 작성자와 세션이 다른 경우 
		if(user_id == null || !writer.equals(user_id)) {

			String path = req.getContextPath();

			res.setContentType("text/html; charset=utf-8");
			PrintWriter out = res.getWriter();
			out.println("<script>");
			out.println("alert('권한이 필요한 기능입니다');");
			out.println("location.href='"+ path + "/board/board_list.board" +"';");
			out.println("</script>");

			return; //함수를 종료하면 컨트롤러로 연결 되지 않음
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}


}
