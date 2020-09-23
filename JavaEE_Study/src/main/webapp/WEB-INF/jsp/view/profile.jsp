<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-12
  Time: 오전 3:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--아래 주석은 개발자들이 사용하는 관례로,
이 페이지의 암시적 범위에 user 변수가 있고 변수의 형식을 알림
이렇게 작성하면 IDE가 자동완성과 지능형 제안을 할 수 있게 되며 유효성 검사도 가능--%>
<%--@elvariable id="user" type="EL.User"--%>
<html>
<head>
    <title>[EL] 암시적 EL 범위 사용 테스트 페이지</title>
</head>
<body>
User ID : ${user.userId} <br>
Username : ${user.username} (${user.username.length()} 글자)<br>
Full name : ${fn:escapeXml(user.lastName) += ', ' += fn:escapeXml(user.firstName)}<br><br>

<b>Permissions (${fn:length(user.permissions)})</b><br>
User : ${user.permissions["user"]}<br>
Moderator : ${user.permissions["moderator"]}<br>
Administrator : ${user.permissions["admin"]}<br>


</body>
</html>
