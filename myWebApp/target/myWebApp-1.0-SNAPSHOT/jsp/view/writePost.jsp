<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-25
  Time: 오전 2:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%--header--%>
<%@ include file="../header.jsp"%>

<div class="container-fluid" style="margin-top: 20px; width: 70%">
    <h1>글 쓰기</h1>

    <form method="POST" action="../action/writePostAction.jsp">
        <div class="form-group">
            <input class="form-control" type="text" name="bTitle" maxlength="50" placeholder="제목" style="height: 50px"/>
        </div>
        <div class="form-group">
            <textarea class="form-control" name="bContent" maxlength="5012" placeholder="내용을 작성하세요." style="height: 500px"></textarea>
        </div>
        <div class="check-box" style="margin-bottom: 20px;">
            Please Check for upload. <input type="checkbox"/>
        </div>
        <input type="submit" value="글 쓰기" style="margin-bottom: 200px"/>
    </form>
</div>


<%--footer--%>
<%@ include file="../footer.jsp"%>