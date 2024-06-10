<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="icon" href="<c:url value="/resources/img/icon.png"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>" type="text/css">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<style></style>
</head>
<body>
	<!-- list.do 는 파라메터가 있을수도 있고 없을수도 있는데? 테스트 해 보면 문제 없다. -->
	<form action="list.do" method="get">
		<select name="opt">
			<option value="id">아이디</option>
			<option value="name">이름</option>
			<option value="email">이메일</option>
		</select>
		<input type="search" name="keyword" placeholder="검색어를 입력 하세요"/>
		<button>search</button>
	</form>
	<table>
	<tr>
		<th>ID</th><th>NAME</th><th>E-MAIL</th>
	</tr>
	<c:forEach items="${list}" var="member">
		<tr>
			<td><a href="detail.do?id=${member.id}">${member.id}</a></td>
			<td>${member.name}</td>
			<td>${member.email}</td>
		</tr>		
	</c:forEach>
	</table>	

</body>
<script>

</script>
</html>