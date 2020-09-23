<%@ page import="User.UserDAO" %>
<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-24
  Time: 오전 3:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--자바빈즈 클래스 User 객체를 이용 (페이지 범위)--%>
<%--로그인 인증확인 시 필요한 userID, userPassword--%>
<jsp:useBean id="user" class="User.User" scope="page"/>
<jsp:setProperty name="user" property="userID"/>
<jsp:setProperty name="user" property="userPassword"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>Title</title>
</head>
<body>

<%
    String userID = null;
    if(session.getAttribute("userID") !=null){
        userID = (String) session.getAttribute("userID");
    }
    if(userID != null){
        PrintWriter writer = response.getWriter();
        writer.println("<script>");
        writer.println("alert('이미 로그인 되어 있습니다.')");
        writer.println("location.href='../../index.html'");
        writer.println("</script>");
    }

    UserDAO userDAO = new UserDAO();

    PrintWriter writer = response.getWriter();
    writer.println("<script>");
    int result = userDAO.login(user.getUserID(), user.getUserPassword());
    if (result == 1) {
        session.setAttribute("userID", user.getUserID());
        writer.println("alert('로그인 성공! 메인페이지로 이동합니다.')");
        writer.println("location.href='../../index.html'");
    } else if (result == 0) {
        writer.println("alert('비밀번호를 잘못 입력하셨습니다.')");
        writer.println("history.back()");
    } else if (result == -1) {
        writer.println("alert('존재하지 않는 ID입니다.')");
        writer.println("history.back()");
    } else if (result == -2) {
        writer.println("alert('DB 오류가 발생하였습니다. 다시 시도해주세요.')");
        writer.println("history.back()");
    }
    writer.println("</script>");
%>
</body>
</html>
