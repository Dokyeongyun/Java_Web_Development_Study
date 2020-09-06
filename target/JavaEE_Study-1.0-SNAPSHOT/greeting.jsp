<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-07
  Time: 오전 12:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private static final String DEFAULT_USER = "손님";
%>
<%
    // 내장 객체 request 사용
    String user = request.getParameter("user");
    if(user==null){
        user = DEFAULT_USER;
    }


%>
<html>
<head>
    <title>내장 객체를 사용하는 방법</title>
</head>
<body>
<h1><%out.println("내장 객체 out 사용하여 출력하기");%></h1>
<h1><%out.println("내장 객체 application 사용하여 컨텍스트 초기화 매개변수 출력하기");
out.println(application.getInitParameter("contextParameter1"));%></h1>
<h1><%out.println("내장 객체 config 사용하여 서블릿 초기화 매개변수 출력하기");
    out.println(config.getInitParameter("servletParameter1"));%></h1>
안녕하세요, <%=user%>!<br><br>
<form action="greeting.jsp" method="POST">
    이름을 입력해주세요: <br>
    <input type="text" name="user"/><br>
    <input type="submit" value="입력"><br>
</form>
</body>
</html>
