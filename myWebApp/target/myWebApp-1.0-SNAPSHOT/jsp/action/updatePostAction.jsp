<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Board.BoardDAO" %>
<%@ page import="Board.Board" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--모든 게시글에는 bPassword가 설정되어 있음--%>
<%--1. 로그인한 사용자가 본인의 글을 수정하려는 경우 : sessionID == bPassword == userID--%>
<%--2. 로그인하지 않은 사용자가 본인의 글을 수정하려는 경우 : bPassword 로 확인--%>
<%--3. 로그인한 사용자가 Guest의 글을 수정하려는 경우--%>
<%@ include file="../header.jsp" %>

<%
    String ok = "false";
    if (request.getParameter("ok") != null) {
        ok = request.getParameter("ok");
    }


    int bID = 0;
    if (request.getParameter("bID") != null) {
        bID = Integer.parseInt(request.getParameter("bID"));
    }

    BoardDAO boardDAO = new BoardDAO();
    Board board = boardDAO.getPost(bID);

    /* 로그인하지 않은 사용자 -> 비밀번호 입력받고 일치하면 수정 */
    if (board.getUserID().equals("Guest") && ok.equals("false") && userID == null) {
%>
<script>
    var userInput = prompt("비밀번호를 입력해주세요" + "");
    if (userInput === "<%=board.getbPassword()%>") {
        if ("<%=board.getUserID()%>" !== "Guest") {
            alert("권한이 없습니다!");
            history.back();
        } else {
            location.href = 'updatePostAction.jsp?ok=true&bID=<%=bID%>';
        }
    } else {
        alert("비밀번호가 일치하지 않습니다!");
        history.back();
    }
</script>
<%
    }
    if(userID == null){
        userID = "Guest";
    }
    if (!userID.equals("Guest")) {
        /* 로그인한 사용자 */
        if (board.getUserID().equals(board.getbPassword())) {
            ok = "true";
        }
    } else {
        userID = "Guest";
    }
    if (ok.equals("true") && userID.equals(board.getUserID())) {
%>
<div class="container-fluid" style="margin-top: 20px; width: 70%">
    <h1>글 수정하기</h1>
    <form method="POST" action="../action/updatePostAction2.jsp?bID=<%=bID%>">
        <div class="form-group">
            <input class="form-control" type="text" name="bTitle" maxlength="200" placeholder="제목" style="height: 50px"
                   value="<%=board.getbTitle()%>"/>
        </div>
        <div class="form-group">
            <textarea class="form-control" name="bContent" maxlength="8192" placeholder="내용을 작성하세요."
                      style="height: 500px"><%=board.getbContent()%></textarea>
        </div>
        <div class="form-inline">
            <div class="check-box">
                Please Check for upload. <input type="checkbox"/>
            </div>
            <%
                if (board.getUserID().equals("Guest")) {
            %>
            <%--로그인하지 않은 사용자에게만 보여주기--%>
            <div class="form-group" style="margin-left: 100px">
                <label> 비밀번호
                    <input type="password" name="bPassword" class="form-control" required/>
                </label>
            </div>
            <%
                }
            %>
        </div>
        <input class="btn btn-primary pull-right" type="submit" value="수정하기" style="margin-bottom: 200px"/>
    </form>
</div>
<%
} else {
%>
<script>
    alert("권한이 없습니다!");
    history.back();
</script>
<%
    }
%>
<%@ include file="../footer.jsp" %>