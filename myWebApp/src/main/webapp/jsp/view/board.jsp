<%@ page import="Board.BoardDAO" %>
<%@ page import="Board.Board" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-24
  Time: 오전 1:35
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--header--%>
<%@ include file="../header.jsp" %>

<%--pageNum 매개변수 받아오기 (없으면 첫 페이지)--%>
<%
    int pageNum = 1;
    if (request.getParameter("pageNum") != null) {
        pageNum = Integer.parseInt(request.getParameter("pageNum"));
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


<%--게시판 목록--%>
<div class="container-fluid" style="margin-outside: 100px; margin-bottom: 150px">
    <h1 style="margin-top: 20px; margin-bottom: 20px">게시판 이름</h1>
    <div class="table-responsive-lg" style="text-align: center">
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th style="width: 5%">번호</th>
                <th style="width: 40%">제목</th>
                <th style="width: 20%;">작성자</th>
                <th style="width: 20%">작성일자</th>
            </tr>
            </thead>
            <tbody>
            <%--글 목록 불러와 띄우기--%>
            <%
                BoardDAO boardDAO = new BoardDAO();
                ArrayList<Board> list = boardDAO.getList(pageNum);
                for (int i = 0; i < list.size(); i++) {
            %>
            <tr>
                <td><%=list.get(i).getbID()%>
                </td>
                <%--TODO 제목이 일정 길이가 넘어가면 그 뒤는 ... 표시로 대체하기--%>
                <td style="text-align: left; padding-left: 50px"><a href="viewPost.jsp?bID=<%=list.get(i).getbID()%>"><%=list.get(i).getbTitle()%>
                </a></td>
                <td><%=list.get(i).getUserID()%>
                </td>
                <td><%=list.get(i).getbDate()%>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <%--page 선택 버튼--%>
    <%
        int postNum = boardDAO.getPostNum();
        int pageCount = (postNum / 10) + 1;
    %>
    <div class="text-center">

    <ul class="pagination" style="justify-content: center">
        <%--이전 페이지 버튼--%>
        <%
            if (pageNum != 1) {
        %>
        <li class="page-item"><a href="board.jsp?pageNum=<%=pageNum -1%>" class="page-link">이전</a>
        </li>
        <%
            }
        %>
        <%--페이지 숫자 버튼--%>
        <%
            for (int i = 0; i < pageCount; i++) {
        %>
        <li class="page-item"><a class="page-link" href="board.jsp?pageNum=<%=i+1%>"><%=i + 1%>
        </a></li>
        <%
            }
        %>
        <%--다음 페이지 버튼--%>
        <%
            if (boardDAO.nextPage(pageNum + 1)) {
        %>
        <Li class="page-item"><a href="board.jsp?pageNum=<%=pageNum +1%>" class="page-link">다음</a></Li>
            <%
            }
        %>
    </ul>
    </div>

    <%--글쓰기 버튼--%>
    <div align="right">
        <button class="btn btn-warning" type="button" onclick="location.href='writePost.jsp' ">글 쓰기</button>
    </div>

</div>


<%--footer--%>
<%@ include file="../footer.jsp" %>