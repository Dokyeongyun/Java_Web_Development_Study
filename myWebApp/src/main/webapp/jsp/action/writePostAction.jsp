<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-25
  Time: 오전 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    PrintWriter writer = response.getWriter();
    writer.println(request.getParameter("bTitle"));
    writer.println(request.getParameter("bContent"));
    writer.println(session.getAttribute("userID"));
%>
</body>
</html>
