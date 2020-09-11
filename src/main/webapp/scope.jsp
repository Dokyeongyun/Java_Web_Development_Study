<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-12
  Time: 오전 4:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("a", "페이지");
    request.setAttribute("a", "요청");
    session.setAttribute("a", "세션");
    application.setAttribute("a", "애플리케이션");

    request.setAttribute("b", "요청");
    session.setAttribute("b", "세션");
    application.setAttribute("b", "애플리케이션");

    session.setAttribute("c", "세션");
    application.setAttribute("c", "애플리케이션");

    application.setAttribute("d", "애플리케이션");
%>
<html>
<head>
    <title>암시적 EL 범위에서 변수가 확인될 때 우선순위를 확인</title>
</head>
<body>
페이지, 요청, 세션, 애플리케이션 변수를 a 에 설정했을 때 <br>
a = ${a}<br />
요청, 세션, 애플리케이션 변수를 b 에 설정했을 때 <br>
b = ${b}<br />
세션, 애플리케이션 변수를 c 에 설정했을 때 <br>
c = ${c}<br />
애플리케이션 변수를 d 에 설정했을 때 <br>
d = ${d}<br />
</body>
</html>
