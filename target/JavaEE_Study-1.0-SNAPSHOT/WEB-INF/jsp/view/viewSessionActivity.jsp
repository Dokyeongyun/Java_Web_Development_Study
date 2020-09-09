<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-10
  Time: 오전 3:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.Vector, Session.PageVisit, java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Session.PageVisit" %>
<%!
    private static String toString(long timeInterval)
    {
        if(timeInterval < 1_000)
            return "less than one second";
        if(timeInterval < 60_000)
            return (timeInterval / 1_000) + " seconds";
        return "about " + (timeInterval / 60_000) + " minutes";
    }
%>
<%
    SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
%>
<!DOCTYPE html>
<html>
<head>
    <title>세션 활동 추적</title>
</head>
<body>
<h2>세션 프로퍼티</h2>
Session ID: <%= session.getId() %><br />
Session is new: <%= session.isNew() %><br />
Session created: <%= f.format(new Date(session.getCreationTime()))%><br />

<h2>이 세션의 페이지 활동기록</h2>
<%
    @SuppressWarnings("unchecked")
    Vector<PageVisit> visits =
            (Vector<PageVisit>)session.getAttribute("activity");

    for(PageVisit visit : visits)
    {
        out.print(visit.getRequest());
        if(visit.getIpAddress() != null)
            out.print(" from IP " + visit.getIpAddress().getHostAddress());
        out.print(" (" + f.format(new Date(visit.getEnteredTimestamp())));
        if(visit.getLeftTimestamp() != null)
        {
            out.print(", stayed for " + toString(
                    visit.getLeftTimestamp() - visit.getEnteredTimestamp()
            ));
        }
        out.println(")<br />");
    }
%>
</body>
</html>
