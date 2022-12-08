package com.example.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.user.model.UserDAO;
import com.example.user.model.UserVO;

public class UserServiceImpl implements UserService{
	//컨트롤러의 역할을 분담
	//가입처리

	UserDAO dao = UserDAO.getInstance();

	public int join(HttpServletRequest request, HttpServletResponse response) {
		String id =request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");

		//아이디 or email 중복 검사 -> 가입 
		int result = dao.idCheck(id, email);
		System.out.println(result);
		if(result>=1) {//중복
			return 1; //중복 의미 반환

		}else {
			//중복x ->가입
			UserVO vo = new UserVO(id,pw,name,email,gender);
			dao.join(vo);
		}
		return 0;
	}

	@Override
	public UserVO login(HttpServletRequest request, HttpServletResponse response) {

		String id =request.getParameter("id");
		String pw = request.getParameter("pw");

		UserVO vo = dao.login(id, pw);


		return vo;
	}

	@Override
	public UserVO getInfo(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();//자바에서 현재 세션 얻는 방법

		String id = (String)session.getAttribute("user_id");


		UserVO vo = dao.getInfo(id);



		return vo;
	}

	@Override
	public int update(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");

		UserDAO dao = UserDAO.getInstance();
		int result = dao.update(id, pw, name, gender);

		if(result == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("user_name", name);
		}

		return result;
	}

	@Override
	public int delete(HttpServletRequest request, HttpServletResponse response) {
		//id가 필요
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("user_id");
		String pw = request.getParameter("pw");


		int result = 0;
		UserVO vo = dao.login(id,pw);
		if(vo != null) {
			result = dao.delete(id);

		}
		if(result==1) {//삭제성공
			session.invalidate();
		}
		return result;
	}


}
