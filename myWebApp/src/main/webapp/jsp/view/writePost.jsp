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
            <input class="form-control" type="text" name="bTitle" maxlength="200" placeholder="제목" style="height: 50px"/>
        </div>
        <div class="form-group">
            <textarea class="form-control" name="bContent" maxlength="8192" placeholder="내용을 작성하세요." style="height: 500px"></textarea>
        </div>
        <div class="form-inline">
            <div class="check-box" >
                Please Check for upload. <input type="checkbox"/>
            </div>
            <%
                if(userID==null){
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
        <input class="btn btn-primary pull-right" type="submit" value="글 쓰기" style="margin-bottom: 200px"/>
    </form>
</div>


<%--footer--%>
<%@ include file="../footer.jsp"%>