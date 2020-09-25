<%@ page import="Board.BoardDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="Board.Board" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-25
  Time: 오후 4:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="board" type="Board.Board"--%>
<%--header--%>
<%@ include file="../header.jsp" %>

<%--글 내용 가져오기--%>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    int bID = 0;
    if (request.getParameter("bID") != null) {
        bID = Integer.parseInt(request.getParameter("bID"));
    }
    Board board = new Board();

    BoardDAO boardDAO = new BoardDAO();
    board = boardDAO.getPost(bID);

    if(board.getbID() != bID){
        PrintWriter writer = response.getWriter();
        writer.println("<script>");
        writer.println("alert('존재하지 않는 게시글입니다.')");
        writer.println("history.back()");
        writer.println("</script>");
    }
%>


<%--글 내용 보여주기--%>
<div class="container-fluid" style="margin-top: 20px; width: 70%">
    <h1><%=board.getbID()%>
        <t></t>
        <%=board.getbTitle()%>
    </h1>
    <div class="form-inline">
        <h2 style="margin-left: 20px"><%=board.getUserID()%>
        </h2>
        <h4 style="margin-left: 30px"><%=board.getbDate()%>
        </h4>
    </div>
    <h4><%=board.getbContent()%>
    </h4>
</div>


<%--footer--%>
<%@ include file="../footer.jsp" %>