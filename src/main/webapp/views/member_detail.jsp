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
		<h3>회원정보</h3>
		<table>
			<tr>
				<th>ID</th>
				<td><input type="text" name="id" value="${info.id}" readonly/></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="text" name="pw" value="${info.pw}"/></td>
			</tr>
			<tr>
				<th>NAME</th>
				<td><input type="text" name="name" value="${info.name}"/></td>
			</tr>
			<tr>
				<th>나이</th>
				<td><input type="text" name="age" value="${info.age}"/></td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<!-- gender CHAR(4), 실제 사용 3byte, 1byte 공백 처리 -->
					남자: <input type="radio" name="gender" value="남" 
					<c:if test="${info.gender eq '남'}"> checked</c:if>
					/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					여자: <input type="radio" name="gender" value="여"
					<c:if test="${info.gender eq '여'}"> checked</c:if>
					/>
				</td>
			</tr>
			<tr>
				<th>EMAIL</th>
				<td><input type="text" name="email" value="${info.email}"/></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="button" value="수정" onclick="update()"/>
					<input type="button" value="리스트" 
						onclick="location.href='list.do'"/>
				</th>
			</tr>
		</table>

	</body>
<script>
// 바뀐값만 수정 요청
function update(){
	var param = {'id':'${info.id}'};
	$('table input').each(function(idx, item){
		//console.log($(item));		
		var type = $(item).attr('type');
		if(type == 'radio'){
			// 뭔가 체크가 바뀌었는데 그 값이 true 라면...
			if($(item)[0].defaultChecked != $(item)[0].checked && $(item)[0].checked){
				param[$(item).attr('name')] = $(item).val();
			}
		}else{
			if($(item)[0].defaultValue != $(item)[0].value){
				param[$(item).attr('name')] = $(item).val();
			}
		}		
	});
	console.log(param);
	
	$.ajax({
		type:'post',
		url:'update.ajax',
		data:param,
		dataType:'JSON',
		success:function(data){
			console.log(data);
			if(data.success){
				alert('수정에 성공 하였습니다.');
			}else{
				alert('수정에 실패 하였습니다.');
			}
			location.href = 'detail.do?id=${info.id}';
		},error:function(e){
			console.log(e)
		}
	});
	
}	
	
	
</script>
</html>