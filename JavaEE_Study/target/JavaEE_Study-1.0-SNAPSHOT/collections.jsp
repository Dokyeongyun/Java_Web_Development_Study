<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-13
  Time: 오전 2:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="EL.User" %>
<%@ page import="java.util.ArrayList" %>

<%
    ArrayList<User> users = new ArrayList<>();
    users.add(new User(10000L, "User1", "KyeongYun", "Do"));
    users.add(new User(20000L, "User11", "DaeRee", "Kim"));
    users.add(new User(30000L, "User231", "JiSung", "Park"));
    users.add(new User(40000L, "User541", "ChanHo", "Jo"));
    users.add(new User(50000L, "User5", "Smith", "Lee"));
    request.setAttribute("users",users);
%>
<html>
<head>
    <title>[EL] 스트림 API를 이용한 컬렉션 접근 테스트 페이지</title>
</head>
<body>
<%--1. filter() 로 username에 1이 포함된 요소 필터링--%>
<%--2. soted() 로 lastname으로 먼저 정렬 후 firstname으로 요소 정렬--%>
<%--3. map() 로 스트림의 요소를 map 객체로 반환--%>
<%--4. toList() 로 스트림의 요소를 List에 담아 반환--%>
${users.stream().filter(u -> fn:contains(u.username, "1"))
                .sorted((u1, u2) -> (x = u1.lastName.compareTo(u2.lastName);
                    x ==0 ? u1.firstName.compareTo(u2.firstName) : x))
                .map(u -> {'username' : u.username, 'first' : u.firstName, 'last' : u.lastName})
                .toList()}
</body>
</html>
