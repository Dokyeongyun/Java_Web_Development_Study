<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-07
  Time: 오전 2:18
  To change this template use File | Settings | File Templates.
--%>

<%@ page session="false" %>
<html>
<head>
    <title>고객 지원 애플리케이션 showTicketForm</title>
</head>
<body>
<h2>티켓 생성하기</h2>
<form method="POST" action="tickets" enctype="multipart/form-data">
    <input type="hidden" name="action" value="create"/>
    이름 <br>
    <input type="text" name="customerName"><br><br>
    주제 <br>
    <input type="text" name="subject"><br><br>
    본문 <br>
    <textarea name="body" rows="5" cols="30"></textarea><br><br>
    <b>파일 첨부</b><br>
    <input type="file" name="file1"/><br><br>
    <input type="submit" value="제출하기"/>
</form>
</body>
</html>
