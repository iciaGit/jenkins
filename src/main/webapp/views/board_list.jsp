<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="icon" href="<c:url value="/resources/img/icon.png"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/common.css"/>" type="text/css">
<style>
</style>
</head>
<body>
	<button onclick = "location.href='write.go'">글쓰기</button>
	<table>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>조회수</th>
			<th>등록일</th>
			<th>삭제</th>
		</tr>
		<c:if test="${list.size()<1}">
			<tr><td colspan="6">작성된 게시글이 없습니다.</td></tr>
		</c:if>
		<c:forEach	items="${list}" var="bbs">
			<tr>
				<td>${bbs.idx}</td>
				<td>
					<a href = "detail.do?idx=${bbs.idx}">${bbs.subject}</a>
					<c:if test="${bbs.file_cnt>0}">
						<img class="icon" src="<c:url value="/resources/img/file.png"/>"/>
					</c:if>   
				</td>
				<td>${bbs.user_name}</td>
				<td>${bbs.bHit}</td>
				<td>${bbs.reg_date}</td>
				<td><a href="delete.do?idx=${bbs.idx}">삭제</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
<script>
	var msg = '${msg}';
	if(msg !=''){
		alert(msg);
	}
</script>
</html>