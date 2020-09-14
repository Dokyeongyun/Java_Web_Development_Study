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
<%--[고객 지원 어플리케이션 Version 5 : JSTL이용하여 자바 코드 대체--%>
<%--<c:out>, <c:if>, <c:forEach> 등 --%>
<h2>티켓 #${ticketId}: <c:out value="${ticket.subject}"/></h2>
<i>고객 이름: <c:out value="${ticket.customerName}"/></i><br><br>
<c:out value="${ticket.body}"/><br><br>
<%--
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
--%>
<c:if test="${ticket.numberOfAttachments >0}">
    첨부 파일:
    <c:forEach items="${ticket.attachments}" var="attachment" varStatus="status">
        <c:if test="${!status.first}">, </c:if>
        <a href="<c:url value="/tickets">
            <c:param name="action" value="download" />
            <c:param name="ticketId" value="${ticketId}"/>
            <c:param name="attachment" value="${attachment.name}"/>
        </c:url>"><c:out value="${attachment.name}" /></a>
    </c:forEach>
</c:if>
    <a href="<c:url value="/tickets"/>"><br>티켓 리스트로 돌아가기</a>
</body>
</html>
