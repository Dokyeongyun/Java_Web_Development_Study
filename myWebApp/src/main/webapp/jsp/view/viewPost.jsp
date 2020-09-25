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

<%--게시판 header--%>
<nav class="navbar navbar-expand-lg navbar-light border-bottom">
    <ul class="navbar-nav mt-2 mt-lg-0">
        <li style="padding-left: 50px;"><a href="#"></a> 글 목록1</li>
        <li style="padding-left: 50px;"><a href="#"></a> 글 목록2</li>
        <li style="padding-left: 50px;"><a href="#"></a> 글 목록3</li>
        <li style="padding-left: 50px;"><a href="#"></a> 글 목록4</li>
    </ul>
</nav>

<div class="container-fluid" style="margin-top: 20px; width: 70%">

    <%--글 내용 보여주기--%>
    <%--Title--%>
    <div class="jumbotron" style="background-color: white; padding: 2rem 1rem">
        <h1><%=board.getbTitle()%></h1>
    </div>

    <%--userID, 작성일자--%>
    <div class="form-inline">
        <h3 style="margin-left: 20px"><%=board.getUserID()%></h3>
        <h6 style="margin-left: 30px; padding-top: 10px"><%=board.getbDate()%></h6>
        <%--Dropdown menu--%>
        <div class="dropdown">
            <button class="btn dropdown-toggle" data-toggle="dropdown">
                설정
            </button>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="#">수정하기</a>
                <a class="dropdown-item" href="#">삭제하기</a>
            </div>
        </div>
    </div><hr>

    <%--Content--%>
        <div id="postBody" style="margin-bottom: 100px">
            <div style="padding: 2rem 1rem">
                <p><%=board.getbContent()%></p>
            </div>
        </div><hr>

    <%--글 목록 버튼--%>
        <div align="right" style="padding-right: 50px">
            <button class="btn btn-warning" type="button" onclick="location.href='board.jsp' ">목록으로</button>
        </div>
</div>


<%--footer--%>
<%@ include file="../footer.jsp" %>