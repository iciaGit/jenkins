<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="icon" href="<c:url value="/resources/img/icon.png"/>">
	<link rel="stylesheet" href="resources/css/common.css" type="text/css">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<style></style>
	</head>
	<body>
		<h3>회원가입</h3>
		<form action="join.do" method="post">
			<table>
				<tr>
					<th>ID</th>
					<td>
						<input type="text" name="id"/>
						<button type="button" onclick="overlayChk()">중복체크</button>
					</td>
				</tr>
				<tr>
					<th>PW</th>
					<td><input type="password" name="pw"/></td>
				</tr>
				<tr>
					<th>NAME</th>
					<td><input type="text" name="name"/></td>
				</tr>
				<tr>
					<th>나이</th>
					<td><input type="text" name="age"/></td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						남자: <input type="radio" name="gender" value="남"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						여자: <input type="radio" name="gender" value="여"/>
					</td>
				</tr>
				<tr>
					<th>EMAIL</th>
					<!-- 적지 않으면 '없음 으로 표시 할 예정' -->
					<td><input type="text" name="email"/></td>
				</tr>
				<tr>
					<th colspan="2">
						<input type="submit" value="회원가입"/>
					</th>
				</tr>
			</table>
		</form>
	</body>
<script>
function overlayChk(){
	$.ajax({
		type:'get',
		url:'overlay.ajax',
		data:{
			id: $('input[name="id"]').val()
		},
		success:function(data){
			if(data.cnt == 0){
				alert('사용 할 수 있는 아이디 입니다.');
			}else{
				alert('이미 사용중인 아이디 입니다.');
				$('input[name="id"]').val('');
				$('input[name="id"]').focus();
			}			
		},error:function(e){
			console.log(e);
		}
	});	
}
</script>
</html>