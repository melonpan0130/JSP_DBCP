<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./view/color.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main page</title>
<link href="./view/style.css" rel="stylesheet" type="text/css">
<%	if(session.getAttribute("memId") == null){ // 비회원 %>
	<script>
	
	function focusIt() {
		document.inform.id.focus();
	}
	
	function checkIt() {
		if(!document.inform.id.value){
			alert("Please write on ID");
			document.inform.id.focus();
			return false;
		}
		if(!document.inform.passwd.value){
			alert("Please write on PW");
			document.inform.passwd.focus();
			return false;
		}
	}
	</script>
</head>
<body bgcolor="<%= bodyback_c %>" onload="focusIt()">
<form name="inform" method="post" action="LoginProc.jsp" onsubmit="return checkIt();">
<table width="500" cellpadding="0" cellspacing="0" align="center" border="3">
	<tr>
		<td width="300" bgcolor="<%= bodyback_c %>" height="20"></td>
		<td width="300" bgcolor="<%= title_c %>"  align="center">ID</td>
		<td width="300" bgcolor="<%= value_c %>" height="20">
			<input type="text" name="id" size="15">
		</td>
	</tr>
	<tr>
		<td rowspan="2" bgcolor="<%= value_c %>" width="300" >This is Main</td>
		<td width="300" bgcolor="<%= title_c %>" align="center">PW</td>
		<td width="300" bgcolor="<%= value_c %>" height="20">
			<input type="text" name="passwd" size="15">
		</td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="<%= title_c %>" align="center">
			<input type="submit" value="Log in">
			<input type="button" value="Join" onclick="javascript:window.location='inputForm.jsp'">
		</td>
	</tr>
</table>

</form>		
<%	} else { // 회원  %>
<table width="500" cellpadding="0" cellspacing="0" align="center" border="3">
	<tr>
		<td width="300" bgcolor="<%= bodyback_c %>" height="20">Welcome</td>
		<td rowspan="3" bgcolor="<%= value_c %>" align="center">
			<%= session.getAttribute("memId") %>님이 로그인 하셨습니다.<br>
			<form method="post" action="Logout.jsp">
				<input type="submit" value="Log out"/>
				<input type="button" value="Modify Info" onclick="javascript:window.location='modify.jsp'">
			</form>
		</td>
	</tr>
	<tr>
		<td rowspan="2" bgcolor="<%= value_c %>" width="300" >
			This is Main
		</td>
	</tr>
</table>
<%	}%>


</body>
</html>