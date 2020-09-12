<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-07
  Time: 오전 2:29
  To change this template use File | Settings | File Templates.
--%>

<%--고객 지원 애플리케이션 v3 부터는 session 사용하므로 true로 변경--%>
<%@ page session="true"%>
<%--@elvariable id="ticketId" type="java.lang.String"--%>
<%--@elvariable id="ticket" type="Project.Ticket"--%>
<%
    // String ticketId = (String) request.getAttribute("ticketId");
    Ticket ticket = (Ticket) request.getAttribute("ticket");
%>
<html>
<head>
    <title>고객 지원 애플리케이션 viewTicket</title>
</head>
<body>

<%--로그아웃--%>
<a href="<c:url value="/login?logout" />">로그아웃</a>

<%--[ 고객 지원 프로젝트 Version 4 ]--%>
<%--식언어를 이용하여 ticketId, ticket.getSubject(), ticket.getCustomerName() 등을 대체--%>
<h2>티켓 #${ticketId}: ${ticket.subject}</h2>
<i>고객 이름: ${ticket.customerName}</i><br><br>
${ticket.body}<br><br>

<%
    if(ticket.getNumberOfAttachments()>0){
    %> 첨부 파일: <%
    int i=0;
    for(Attachment a : ticket.getAttachments()){
        if(i++>0) {
            out.print(",");
        }
        %><a href="<c:url value="/tickets">
            <c:param name="action" value="download" />
            <c:param name="ticketId" value="${ticketId}"/>
            <c:param name="attachment" value="<%=a.getName()%>"/>
          </c:url>"><%=a.getName()%></a><%
      }
    }
    %>
    <a href="<c:url value="/tickets"/>"><br>티켓 리스트로 돌아가기</a>
</body>
</html>
