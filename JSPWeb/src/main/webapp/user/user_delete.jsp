<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<section>
	<div align="center">
		<form action="deleteForm.user" method="post">
		
			
			<input type="password" name="pw" placeholder="비밀번호" ><br>
			
			<input type="submit" value="탈퇴">
		</form>

	</div>
</section>

<%@include file="../include/footer.jsp"%>
