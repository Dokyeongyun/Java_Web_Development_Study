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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

<div class="container-fluid" style="margin-top: 20px; width: 70%; margin-bottom: 150px">

    <%--글 내용 보여주기--%>
    <%--Title, 크로스사이트스크립팅 방지--%>
    <div class="jumbotron" style="background-color: white; padding: 2rem 1rem">
        <h1><c:out value="<%=board.getbTitle()%>"/></h1>
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
                <a class="dropdown-item" href="../action/updatePostAction.jsp?bID=<%=board.getbID()%>">수정하기</a>
                <a onclick="return confirm('정말 삭제하시겠습니까?')" class="dropdown-item" href="../action/deletePostAction.jsp?bID=<%=board.getbID()%>">삭제하기</a>
            </div>
        </div>
    </div><hr>

    <%--Content, 크로스사이트스크립팅 방지--%>
        <div id="postBody" style=" min-height: 400px">
            <div style="padding: 2rem 1rem">
                <%--textarea에 입력된 개행문자를 실행하기 위해 <pre>태그사용--%>
                <pre><c:out value="<%=board.getbContent()%>"/></pre>
            </div>
        </div><hr>

    <%--글 목록 버튼--%>
        <div align="right" style="padding-right: 50px">
            <button class="btn btn-warning" type="button" onclick="location.href='board.jsp' ">목록으로</button>
        </div>
</div>


<%--footer--%>
<%@ include file="../footer.jsp" %>