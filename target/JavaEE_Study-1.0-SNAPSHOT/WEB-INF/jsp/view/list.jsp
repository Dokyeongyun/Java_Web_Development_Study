<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-13
  Time: 오후 9:24
  To change this template use File | Settings | File Templates.
--%>

<%--@elvariable id="contacts" type="java.util.Set<JSTL.Contact>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>[JSTL] 전화번호부</title>
</head>
<body>
<h2>전화번호부</h2>
<%--JSTL 조건문 <c:choose> <c:when> <c:otherwise>--%>
<c:choose>
    <c:when test="${fn:length(contacts)==0}">
        <i>전화번호부에 저장된 연락처가 없습니다.</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${contacts}" var="contact">
            이름: <b>
                <c:out value="${contact.lastName}, ${contact.firstName}" />
            </b><br>
            주소: <c:out value="${contact.address}" /><br>
            전화번호: <c:out value="${contact.phoneNumber}" /><br>
            <c:if test="${contact.birthday !=null}">
                생년월일: <c:out value="${contact.birthday}" /><br>
            </c:if>
            생성일자: ${contact.dateCreated}<br><br>
        </c:forEach>
    </c:otherwise>

</c:choose>



</body>
</html>
