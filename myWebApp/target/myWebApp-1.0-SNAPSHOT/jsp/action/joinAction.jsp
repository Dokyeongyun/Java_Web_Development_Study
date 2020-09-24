<%@ page import="User.UserDAO" %>
<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-24
  Time: 오후 7:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:useBean id="user" class="User.User" scope="page"/>
<jsp:setProperty name="user" property="userID"/>
<jsp:setProperty name="user" property="userPassword"/>
<jsp:setProperty name="user" property="userEmail"/>
<jsp:setProperty name="user" property="userGender"/>
<jsp:setProperty name="user" property="userName"/>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    PrintWriter writer = response.getWriter();
    writer.println("<script>");
    if (user.getUserID() != null && user.getUserPassword() != null && user.getUserName() != null
            && user.getUserEmail() != null && user.getUserGender() != null) {

        if (request.getParameter("userPasswordChk").equals(user.getUserPassword())) {
            UserDAO userDAO = new UserDAO();
            int result = userDAO.join(user);
            if (result != -1) {
                // 성공
                writer.println("alert('축하합니다! 회원가입에 성공하셨습니다.')");
                writer.println("location.href='../../index.jsp'");
            } else {
                // ID 중복
                writer.println("alert('사용하실 수 없는 ID입니다')");
                writer.println("history.back()");
            }
        } else {
            // 패스워드 확인 불일치
            writer.println("alert('비밀번호 확인이 일치하지 않습니다. 다시 확인해주세요!')");
            writer.println("history.back()");
        }
        writer.println("</script>");
    } else {
        writer.println("alert('입력되지 않은 정보가 있습니다. 다시 확인해주세요!')");
        writer.println("history.back()");
        writer.println("</script>");
    }
%>
</body>
</html>
