<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-07
  Time: 오전 2:18
  To change this template use File | Settings | File Templates.
--%>
<%--고객 지원 애플리케이션 v3 부터는 session 사용하므로 true로 변경--%>
<%@ page session="true" %>


<%--[고객 지원 어플리케이션 Version 6 : 템플릿 태그파일 이용한 자바 코드 대체--%>
<%--
<html>
<head>
    <title>고객 지원 애플리케이션 showTicketForm</title>
</head>
<body>
--%>
<template:basic htmlTitle="티켓 생성하기" bodyTitle="티켓 생성하기">

<%--로그아웃--%>
<a href="<c:url value="/login?logout" />">로그아웃</a>

<h2>티켓 생성하기</h2>
<form method="POST" action="tickets" enctype="multipart/form-data">
    <input type="hidden" name="action" value="create"/>
    <%--Version3 부터 로그인시에만 티켓 생성이 가능하므로 사용자 이름을 입력받을 필요가 없다--%>
<%--    이름 <br>
    <input type="text" name="customerName"><br><br>--%>
    주제 <br>
    <input type="text" name="subject"><br><br>
    본문 <br>
    <textarea name="body" rows="5" cols="30"></textarea><br><br>
    <b>파일 첨부</b><br>
    <input type="file" name="file1"/><br><br>
    <input type="submit" value="제출하기"/>
</form>
</template:basic>


<%--
</body>
</html>
--%>
