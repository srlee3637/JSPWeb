<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<%@taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c"%>   

<div align="center">
	<form action="updateForm.user" method="post">
		<span style="color: red;">${msg }</span>
		<table>
			<tr>
				<td>아이디</td>
				<td><input type="text" name="id" placeholder="4~8글자 영문자 숫자"
					pattern="\w{4,8}" required="required" value="${requestScope.vo2.id}" readonly></td>
			</tr>

			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pw" pattern="\w{4,8}"
					required="required" value="${requestScope.vo2.pw}"></td>
			</tr>

			<tr>
				<td>이름</td>
				<td><input type="text" name="name" pattern="[가-힣]{3,}" value="${requestScope.vo2.name}">
				</td>
			</tr>

			<tr>
				<td>이메일</td>
				
				<td><input type="email" name="email" value="${requestScope.vo2.email}" readonly></td>
			</tr>

			<tr>
				<td>성별</td>
				
				<td>
					<input type="radio" name="gender" value="f" ${requestScope.vo2.gender == 'f'? 'checked': ''}>여자 
					<input type="radio" name="gender" value="m" ${requestScope.vo2.gender == 'm'? 'checked': ''}>남자
				</td>
			</tr>

		</table>
		<input type="submit" value="정보수정"> 
		<input type="button" value="마이페이지" onclick="location.href='user_mypage.user'">

	</form>
</div>


<%@include file="../include/footer.jsp"%>
