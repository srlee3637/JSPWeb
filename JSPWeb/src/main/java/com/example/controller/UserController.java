package com.example.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.user.model.UserVO;
import com.example.user.service.UserServiceImpl;

@WebServlet("*.user")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	//get , post 하나로 묶음
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글 처리
		request.setCharacterEncoding("utf-8");

		//요청분기
		String uri = request.getRequestURI();
		String path = request.getContextPath();

		String command = uri.substring(path.length());

		System.out.println("요청경로:" + command);
		UserServiceImpl service = new UserServiceImpl();
		HttpSession session;//자바에서 현재 세션 얻는 방법
		switch(command) {

		case "/user/user_join.user":

			request.getRequestDispatcher("user_join.jsp").forward(request, response);//파일의 경로 

			break;


		case "/user/joinForm.user"://회원 가입 기능

			int result = service.join(request, response);
			if(result>=1) {//아이디 중복
				//메시지
				request.setAttribute("msg", "중복된 아이디 or email입니다");
				request.getRequestDispatcher("user_join.jsp").forward(request, response);//파일의 경로 

			}else {//가입성공
				//request.getRequestDispatcher("user_login.jsp").forward(request, response);//파일의 경로 
				//MVC2에서 리다이렉트는 다시 컨트롤러를 태우는 요청 
				response.sendRedirect("user_login.user");
			}

			break;

		case "/user/user_login.user"://로그인 페이지
			request.getRequestDispatcher("user_login.jsp").forward(request, response);//파일의 경로 
			break;

		case "/user/loginForm.user"://로그인 요청

			UserVO vo =  service.login(request, response);
			System.out.println(vo);
			if(vo==null) {//로그인 실패
				request.setAttribute("msg", "아이디와 비밀번호를 확인하세요");
				request.getRequestDispatcher("user_login.jsp").forward(request, response);
				
			}else {//로그인 성공 
				//세션에 아이디, 이름 저장
				System.out.println("로그인 성공");
				session = request.getSession();
				session.setAttribute("user_id", vo.getId());
				session.setAttribute("user_name", vo.getName());
				
				
				
				//마이페이지
				response.sendRedirect("user_mypage.user");

			}
			break;
			
		case "/user/user_mypage.user"://마이 페이지
			request.getRequestDispatcher("user_mypage.jsp").forward(request, response);//파일의 경로 
			break;	

			
		case "/user/user_logout.user"://로그아웃
			session = request.getSession();
			session.invalidate();//세션 무효화
			
			//response.sendRedirect("/JSPWeb/index.main");//메인으로
			response.sendRedirect(path + "/index.main");
			
			break;
			
		case "/user/user_modify.user"://정보수정화면
			/*****회원 데이터를 가지고 나오는 작업*****
			 * service와 dao에 getInfo()메서드를 선언
			 * service에서는 세션에서 아이디를 얻는다
			 * dao에서는 id를 전달받아 회원 데이터를 조회하여 vo에 저장
			 * controller에서는 조회한 vo를 저장하고 화면으로 가지고 나감
			 * 화면에서는 input태그에 값을 출력
			 * */
			UserVO vo2 =  service.getInfo(request, response);
			request.setAttribute("vo2", vo2);
			System.out.println(vo2.getGender());
			request.getRequestDispatcher("user_modify.jsp").forward(request, response);
			
			break;
			
		case "/user/updateForm.user" :
	         /*
	          * 회원정보를 업데이트하는 작업
	          * service와 dao에 update() 메서드를 생성
	          * service의 필요한 파라미터 값을 받는다(pw, name, gender) 조건절(id)
	          * dao에서 데이터를 전달받아서 업데이트를 실행
	          * 업데이트 이후에는 컨트롤러를 태워서 mypage로 리다이렉트 
	          */
			
			int result2 = service.update(request, response);
			if(result2==1) {//업데이트 성공
				
				//response.sendRedirect("user_mypage.user");
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('정보가 수정되었습니다');");
				out.println("location.href='user_mypage.user';");
				out.println("</script>");
				
			}else {// 업데이트 실패
				request.setAttribute("msg", "양식에 맞춰 작성해주세요");
				response.sendRedirect("user_modify.user");
				
				//out객체를 이용해서 화면에 스크립트를 작성해서 보냄
				
			}
	         
	      break;
	      
		case "/user/deleteForm.user" :
	        
			int result3 = service.delete(request, response);
			if(result3 == 1) {
				response.sendRedirect(path + "/index.main");
			}else {
				response.sendRedirect("user_delete.user");
			}
	         
	      break;
		
	  	
		case "/user/user_delete.user":
			request.getRequestDispatcher("user_delete.jsp").forward(request, response);
			
			break;  
	      
		default:
			break;
		}
	}





}









