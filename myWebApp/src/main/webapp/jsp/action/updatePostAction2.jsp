<%@ page import="java.io.PrintWriter" %>
<%@ page import="Board.BoardDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String userID = null;
    if (session.getAttribute("userID") != null) {
        userID = (String) session.getAttribute("userID");
    }
    int bID = Integer.parseInt(request.getParameter("bID"));
    String bTitle = request.getParameter("bTitle");
    String bContent = request.getParameter("bContent");
    String bPassword = request.getParameter("bPassword");

    // TODO 로그인 안했을 시 처리 + 입력 값 없을 시 예외처리
    if (userID != null) {
        bPassword = userID;
    }
    if (userID == null) {
        userID = "Guest";
    }

    PrintWriter writer = response.getWriter();

    BoardDAO boardDAO = new BoardDAO();
    int result = boardDAO.update(bID, bTitle, bContent, bPassword);

    writer.println("<script>");
    if (result != -1) {
        writer.println("alert('성공')");
        writer.println("location.href = '../view/board.jsp'");
    } else {
        writer.println("alert('실패')");
        writer.println("history.back()");
    }
    writer.println("</script>");
%>
