<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-07
  Time: 오전 2:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page session="false" import="java.util.Map" %>
<%
    Map<Integer, Ticket> ticketDatabase = (Map<Integer,Ticket>)request.getAttribute("ticketDatabase");
%>
<html>
<head>
    <title>고객 지원 애플리케이션 listTickets</title>
</head>
<body>
<h2>티켓 리스트</h2>
<a href="<c:url value="/tickets">
<c:param name="action" value="create" />
</c:url>">티켓 생성하기</a><br><br>
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
</body>
</html>
