<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-10
  Time: 오후 2:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%!
    private static String toString(long timeInterval)
    {
        if(timeInterval < 1_000)
            return "1초 이내";
        if(timeInterval < 60_000)
            return (timeInterval / 1_000) + " 초";
        return "약 " + (timeInterval / 60_000) + " 분";
    }
%>
<%
    int numberOfSessions = (Integer)request.getAttribute("numberOfSessions");
    @SuppressWarnings("unchecked")
    List<HttpSession> sessions = (List<HttpSession>)request.getAttribute("sessionList");
%>
<html>
<head>
    <title>고객 지원 어플리케이션 세션 활동 로그</title>
</head>
<body>
<a href="<c:url value="/login?logout" />">로그아웃</a>
<h2>세션 활동 로그</h2>
현재 어플리케이션에 총 <%= numberOfSessions %> 개의 세션이 활성화되어있습니다.<br /><br />
<%
    long timestamp = System.currentTimeMillis();
    for(HttpSession aSession : sessions)
    {
        out.print(aSession.getId() + " - " + aSession.getAttribute("username"));
        if(aSession.getId().equals(session.getId()))
            out.print(" (you)");
        out.print(" - 마지막 활동 시간 " + toString(timestamp - aSession.getLastAccessedTime()));
        out.println(" 전<br />");
    }
%>
<br><br><br><br>
<a href="<c:url value="/tickets" />">되돌아가기</a>

</body>
</html>
