<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-10
  Time: 오전 1:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>[과자가게] 상품 리스트</title>
</head>
<body>
<h2>상품 리스트</h2>
<a href="<c:url value="/shop?action=viewCart" />">장바구니 보기</a><br/><br/>
<%
    @SuppressWarnings("unchecked")
    Map<Integer, String> products =
            (Map<Integer, String>) request.getAttribute("products");

    for (int id : products.keySet()) {
        %><a href="<c:url value="/shop">
        <c:param name="action" value="addToCart"/>
        <c:param name="productId" value="<%=Integer.toString(id)%>"/>
        </c:url>"><%=products.get(id)%></a><br/><%
    }
%>
<br><br><br>
<a href="<c:url value="/shop?action=changeSessionId" />">세션 변경하기</a><br/><br/>
</body>
</html>
