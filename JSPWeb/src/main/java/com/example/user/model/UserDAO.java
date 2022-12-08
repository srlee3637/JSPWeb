package com.example.user.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.websocket.Session;

import com.example.util.JDBCUtil;

public class UserDAO {

	//UserDAO 는 불필요하게 여러개 만들어질 필요가 없기 때문에
	//하나의 객체만 만들어 지도록 Singleton형식으로 설계
	
	//1. 나자신의 객체를 생성해서 1개로 제한
	private static UserDAO instance = new UserDAO();
	
	//2. 직접 객체를 생성 할 수 없도록 생성자에 private
	private UserDAO() {
		//드라이버 클래스 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버클래스 로드에러");
		}
		
	}
	
	//3. 외부에서 객체 생성을 요구할 때 getter메서드를 통해 1번의 객체를 반환
	public static UserDAO getInstance() {
		return instance;
	}
	
	//4.필요한 데이터 베이스 변수 선언
	public String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public String UID = "jsp";
	public String UPW = "jsp";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	//5.메서드
	//아이디 이메일 중복 체크
	public int idCheck(String id , String email) {
		int result = 0;
		
		String sql = "select count(*) as total from users where id=? or email=?";
	
		try {
			//1.conn 객체
			conn = DriverManager.getConnection(URL,UID,UPW);
			
			//2.pstmt
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, email);
			
			//3.실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("total");
			}
			
			
		} catch (Exception e) {
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		
		return result;
	
	}
	
	//회원가입
	public void join(UserVO vo){
		String sql = "insert into users values(?,?,?,?,?)";
		
		try {
			conn = DriverManager.getConnection(URL,UID,UPW);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getGender());
			
			
			pstmt.executeUpdate();
			//실행(성공시 1 실패시 0반환) 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
	}
	
	//login
	public UserVO login(String id, String pw) {
		UserVO vo = null;
		String sql = "select * from users where id = ? and pw = ?";
		try {
			conn = DriverManager.getConnection(URL,UID,UPW);			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs =pstmt.executeQuery();
			
			if(rs.next()) {//로그인 성공 후 vo에 회원정보 저장
				
				String id2 = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String gender = rs.getString("gender");
				
				vo = new UserVO(id2, null, name, email, gender);
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		
		return vo;
	}
	
	//정보 가져오기 
	public UserVO getInfo(String id) {
		UserVO vo = null;
		String sql = "select * from users where id = ?";
		
		try {
			conn = DriverManager.getConnection(URL,UID,UPW);			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			
			
			rs =pstmt.executeQuery();
			
			if(rs.next()) {//로그인 성공 후 vo에 회원정보 저장
				
				String id2 = rs.getString("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String gender = rs.getString("gender");
				
				vo = new UserVO(id2, null, name, email, gender);
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return vo;
	}
	
	
	//정보 수정 
	public int update(String id, String pw, String name, String gender) {
		String sql = "update users set pw = ?, name = ? , gender = ? where id = ?";
		int result = 0;
		try {
			conn = DriverManager.getConnection(URL,UID,UPW);			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, pw);
			pstmt.setString(2, name);
			pstmt.setString(3, gender);
			pstmt.setString(4, id);
			
			
			result = pstmt.executeUpdate();
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return result; 
		
	}
	
	//회원 탈퇴
	public int delete(String id) {
		String sql = "delete from users where id = ? ";
		int result = 0;
		try {
			conn = DriverManager.getConnection(URL,UID,UPW);			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);

			result = pstmt.executeUpdate();
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}



	
	
}
