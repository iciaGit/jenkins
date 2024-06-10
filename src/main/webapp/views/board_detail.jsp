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
	<!-- 보여주기만할꺼라서 폼이 빠짐 -->
	<table>
		<tr>
			<th>글번호</th>
			<td>${bbs.idx}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${bbs.bHit}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${bbs.subject}</td>
		</tr>	
		<tr>
			<th>작성자</th>
			<td>${bbs.user_name}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${bbs.content}</td>
		</tr>
		<c:if test="${fileList.size() > 0}">
		<tr>
			<th>파일</th>
			<td>
			<c:forEach items="${fileList}" var="map">
				<c:if test="${map.type == 'image' }">
					<img width="300px" src="/file/${map.new_filename}"/>
				</c:if>
				<c:if test="${map.type != 'image' }">
					${map.ori_filename}
					<button onclick="location.href='download.do?fileName=${map.new_filename}'"> 다운로드</button>
				</c:if>		
				<br/><br/>
			</c:forEach>		
			</td>
		</tr>		
		</c:if>
		<tr>
			<th colspan="2">
				<input type= button onclick="location.href='list.do'" value="목록"/>
			</th>
		</tr>
	</table>
</body>
<script>
</script>
</html>