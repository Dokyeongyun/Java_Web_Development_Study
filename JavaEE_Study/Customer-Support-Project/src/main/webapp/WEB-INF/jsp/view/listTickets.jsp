<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-07
  Time: 오전 2:44
  To change this template use File | Settings | File Templates.
--%>

<%--고객 지원 애플리케이션 v3 부터는 session 사용하므로 true로 변경--%>
<%@ page session="true" import="java.util.Map" %>

<%--@elvariable id="ticketDatabase" type="java.util.Map<Integer, Project.Ticket>"--%>
<%--
<%
    Map<Integer, Ticket> ticketDatabase = (Map<Integer,Ticket>)request.getAttribute("ticketDatabase");
%>
--%>

<%--[고객 지원 어플리케이션 Version 6 : 템플릿 태그파일 이용한 자바 코드 대체 + 메서드를 EL함수로 선언하여 손쉽게 사용--%>
<%--
<html>
<head>
    <title>고객 지원 애플리케이션 listTickets</title>
</head>
<body>
--%>
<template:basic htmlTitle="티켓 리스트" bodyTitle="티켓 리스트">
    <c:choose>
        <c:when test="${fn:length(ticketDatabase) ==0}">
            <i>시스템에 저장된 티켓이 없습니다.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${ticketDatabase}" var="entity">
                티켓 # ${entity.key}: <a href="<c:url value="/tickets">
                <c:param name="action" value="view" />
                <c:param name="ticketId" value="${entity.key}" />
                </c:url>">
                <c:out value="${dft:abbreviateString(entity.value.subject,60)}" /></a><br>
                (이름: <c:out value="${entity.value.customerName}"/>)
                <dft:formatDate value="${entity.value.dateCreated}" type="both"
                timeStyle="short" dateStyle="medium" /><br>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <br><br><br><br>
<%--
    <a href="<c:url value="/sessions" />">세션 활동 로그보기</a>
--%>

</template:basic>

<%--
로그아웃
<a href="<c:url value="/login?logout" />">로그아웃</a>

<h2>티켓 리스트</h2>
<a href="<c:url value="/tickets">
<c:param name="action" value="create" />
</c:url>">티켓 생성하기</a><br><br>
--%>

<%--[고객 지원 어플리케이션 Version 5 : JSTL이용하여 자바 코드 대체--%>
<%--

<%
    if(ticketDatabase.size()==0){
        %> <i>시스템에 저장된 티켓이 없습니다.</i><%
    }else{
        for(int id : ticketDatabase.keySet()){
            String idString = Integer.toString(id);
            Ticket ticket = ticketDatabase.get(id);
            %> 티켓 #<%=idString%>: <a href="<c:url value="/tickets">
            <c:param name="action" value="view"/>
            <c:param name="ticketId" value="<%=idString%>"/>
            </c:url> "><%=ticket.getSubject()%></a>
            (이름: <%=ticket.getCustomerName()%>)<br><%
        }
    }
%>
--%>

<%--
</body>
</html>
--%>
