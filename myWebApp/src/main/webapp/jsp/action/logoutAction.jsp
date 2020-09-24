<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-24
  Time: 오후 7:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    session.invalidate();
%>
<script>
    alert("로그아웃되었습니다.");
    location.href = "/myWebApp_war/index.jsp";
</script>
</body>
</html>
