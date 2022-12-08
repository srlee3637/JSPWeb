package com.example.board.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.example.user.model.UserVO;
import com.example.util.JDBCUtil;

public class BoardDAO {
	//UserDAO 는 불필요하게 여러개 만들어질 필요가 없기 때문에
	//하나의 객체만 만들어 지도록 Singleton형식으로 설계

	//1. 나자신의 객체를 생성해서 1개로 제한
	private static BoardDAO instance = new BoardDAO();

	//2. 직접 객체를 생성 할 수 없도록 생성자에 private
	private BoardDAO() {
		//드라이버 클래스 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버클래스 로드에러");
		}

	}

	//3. 외부에서 객체 생성을 요구할 때 getter메서드를 통해 1번의 객체를 반환
	public static BoardDAO getInstance() {
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
	public void regist(String writer, String title, String content){

		String sql = "insert into board(bno, writer, title, content) values(board_seq.nextval,?,?,?)";

		try {

			conn = DriverManager.getConnection(URL,UID,UPW);
			pstmt = conn.prepareStatement(sql);


			pstmt.setString(1, writer);
			pstmt.setString(2, title);
			pstmt.setString(3, content);


			pstmt.executeUpdate();
			//실행(성공시 1 실패시 0반환) 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
	}



	public ArrayList<BoardVO> getList(){


		ArrayList<BoardVO> list = new ArrayList<>();		
		String sql = "select * from board order by bno desc";

		try {

			conn = DriverManager.getConnection(URL,UID,UPW);
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				int bno = rs.getInt("bno");
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				String content = rs.getString("content");
				Timestamp regdate = rs.getTimestamp("regdate");
				int hit = rs.getInt("hit");

				BoardVO vo = new BoardVO(bno, writer, title, content, regdate, hit);
				list.add(vo);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}

		return list;
	}

	public BoardVO getContent(String bno) {
		BoardVO vo = null;
		String sql = "select * from board where bno =?";

		try {

			conn = DriverManager.getConnection(URL,UID,UPW);
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, bno);//꼭 setInt 로 파싱 안해도 된다.

			rs = pstmt.executeQuery();

			if(rs.next()) {
				
				vo = new BoardVO();
				vo.setBno(rs.getInt("bno"));
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setContent(rs.getString("content"));
				vo.setRegdate(rs.getTimestamp("regdate"));
				vo.setHit(rs.getInt("hit"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}

		return vo;
	}
	
	public void hitCount(String bno) {
		
		String sql = "update board set hit = hit + 1 where bno = ?";

		try {

			conn = DriverManager.getConnection(URL,UID,UPW);
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, bno);//꼭 setInt 로 파싱 안해도 된다.

			pstmt.executeUpdate();

			

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn, pstmt, rs);
		}

		
	}
	
	public void update(String title, String content, String bno) {
		String sql = "update board set title = ? , content = ? where bno = ?";
		
		try {
			conn = DriverManager.getConnection(URL,UID,UPW);			
			pstmt = conn.prepareStatement(sql);

			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, bno);
			
			
			pstmt.executeUpdate();
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		
		
	}
	
	public int delete(String bno) {
		String sql = "delete from board where bno = ?";
		int result = 0;
		try {
			conn = DriverManager.getConnection(URL,UID,UPW);			
			pstmt = conn.prepareStatement(sql);

			
			
			pstmt.setString(1, bno);
			
			
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


