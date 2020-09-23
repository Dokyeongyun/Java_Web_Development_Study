<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-10
  Time: 오전 3:29
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="loginFaild" type="java.lang.Boolean"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--[고객 지원 어플리케이션 Version 6 : 템플릿 태그파일 이용한 자바 코드 대체--%>
<template:loggedOut htmlTitle="로그인" bodyTitle="로그인">
    고객 지원 사이트에 접속하려면 로그인을 해주세요.<br><br>
<%--
<html>
<head>
    <title>고객 지원 애플리케이션 로그인</title>
</head>
<body>
<h2>로그인</h2>
고객 지원 사이트에 접속하려면 로그인을 해주세요.<br><br>
--%>


<%--[고객 지원 어플리케이션 Version 5 : JSTL이용하여 자바 코드 대체--%>
<%--
<%
    if (((Boolean) request.getAttribute("loginFailed"))) {
%>
<b>입력하신 이름과 비밀번호가 정확하지 않습니다. 다시 시도해주세요.</b><br><br>
<%
    }
%>
--%>
<c:if test="${loginFaild}">
    <b>입력하신 이름과 비밀번호가 정확하지 않습니다. 다시 시도해주세요.</b><br><br>
</c:if>


<form method="POST" action="<c:url value="/login"/>">
    이름<br>
    <input type ="text" name="username" /><br><br>
    비밀번호<br>
    <input type="password" name="password" /><br><br>
    <input type="submit" value="로그인" />
</form>

<%--
</body>
</html>
--%>

</template:loggedOut>
