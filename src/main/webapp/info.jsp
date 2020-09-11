<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-12
  Time: 오전 4:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    application.setAttribute("appAttribute", "애플리케이션 범위");
    pageContext.setAttribute("pageAttribute", "페이지 범위");
    session.setAttribute("sessionAttribute", "세션 범위");
    request.setAttribute("requestAttribute", "요청 범위");
%>
<html>
<head>
    <title>Information</title>
</head>
<body>
Remote Address: ${pageContext.request.remoteAddr}<br/>
Request URL: ${pageContext.request.requestURL}<br/>
Session ID: ${pageContext.request.session.id}<br/>
Application Scope: ${applicationScope["appAttribute"]}<br/>
Page Scope: ${pageScope["pageAttribute"]}<br/>
Session Scope: ${sessionScope["sessionAttribute"]}<br/>
Request Scope: ${requestScope["requestAttribute"]}<br/>
User Parameter: ${param["user"]}<br/>
Color Multi-Param: ${fn:join(paramValues["colors"], ', ')}<br/>
Accept Header: ${header["Accept"]}<br/>
Session ID Cookie Value: ${cookie["JSESSIONID"].value}<br/>
</body>
</html>