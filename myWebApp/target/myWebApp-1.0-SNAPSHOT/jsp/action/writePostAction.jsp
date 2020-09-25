<%@ page import="java.io.PrintWriter" %>
<%@ page import="Board.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-25
  Time: 오전 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--header--%>
<%@ include file="../header.jsp" %>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    PrintWriter writer = response.getWriter();
    writer.println("<script>");

    String bTitle = request.getParameter("bTitle");
    String bContent = request.getParameter("bContent");

    // TODO 로그인 안했을 시 처리 + 입력 값 없을 시 예외처리
    BoardDAO boardDAO = new BoardDAO();
    int result = boardDAO.writePost(bTitle, userID, bContent);
    if(result!=-1){
        writer.println("alert('성공')");
    }else{
        writer.println("alert('실패')");
        writer.println("history.back()");
    }
    writer.println("</script>");


%>

<%--footer--%>
<%@ include file="../footer.jsp" %>
