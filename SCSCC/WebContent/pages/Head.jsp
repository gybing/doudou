<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.doudoumobile.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Eton kids</title>

<link href="../css/main.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="header">
		<div id="logo">
			<img src="../img/etonkids-logo.png"/>
			<div style="float:right;font-size:30px;font-weight:bold;text-align:center;line-height:120px">Etonkids iTeaching Management System</div>
			
		</div>
		<div id="menu">
			<div id="list">
				<ul>
					<li><a href="Lesson.jsp" target="mainbox">Curricula</a></li>
					<li><a href="Security.jsp" target="mainbox">Security</a></li>
<c:choose>

<c:when test="${SessionData.etonUser.role==0 || SessionData.etonUser.role==1}">
	<li><a href="Setting.jsp" target="mainbox">Settings</a></li>
</c:when>

</c:choose>
					
				</ul>
			</div>
			<div id="account">
				<ul>
					<li><a href="PersonalSetting.jsp" target="mainbox">Personal Settings</a></li>
				
					<li><a href="../Login.jsp" target="_parent">Sign out</a></li>
				</ul>
				
			</div>
		</div>
	</div>
</body>
</html>
