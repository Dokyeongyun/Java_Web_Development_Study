<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-10
  Time: 오전 1:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>[과자가게] 장바구니 보기</title>
</head>
<body>
<h2>[과자가게] 장바구니 보기</h2>
<a href="<c:url value="/shop" />">상품 리스트</a><br><br>
<%
    Map<Integer, String> products = (Map<Integer, String>)request.getAttribute("products");

    // 내장객체 session 을 이용하여 세션에 접근
    Map<Integer, Integer> cart = (Map<Integer,Integer>)session.getAttribute("cart");

    if(cart == null || cart.size()==0){
        out.println("장바구니에 담은 상품이 없습니다.");
    }else{
        for(int id : cart.keySet()){
            out.println(products.get(id)+ " (수량: "+cart.get(id)+")<br>");
        }
    }
%><br><br>
<a href="<c:url value="/shop?action=emptyCart" />">장바구니 비우기</a><br><br>

</body>
</html>
