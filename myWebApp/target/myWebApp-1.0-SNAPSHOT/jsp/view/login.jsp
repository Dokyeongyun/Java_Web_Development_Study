<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-24
  Time: 오전 2:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../header.jsp" %>
<%

    if (userID != null) {
        PrintWriter writer = response.getWriter();
        writer.println("<script>");
        writer.println("alert('이미 로그인 되어 있습니다.')");
        writer.println("location.href='../../index.jsp'");
        writer.println("</script>");
    }

%>

<div class="container-fluid" style="margin-top: 20px;">
    <div class="col-lg-4"></div>
    <div class="col-lg-4" style="margin-left: auto; margin-right: auto;">
        <div class="jumbotron" style="padding-top: 20px;">
            <form method="post" action="../action/loginAction.jsp">
                <h3 style="text-align: center;">로그인</h3>
                사용자 ID
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="ID" name="userID" maxlength="20">
                </div>
                비밀번호
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="Password" name="userPassword">
                </div>
                <input type="submit" class="btn btn-primary form-control" value="로그인">
            </form>
        </div>
    </div>
    <div class="col-lg-4"></div>
</div>

<%@include file="../footer.jsp"%>