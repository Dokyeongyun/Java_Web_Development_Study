<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-24
  Time: 오전 2:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--header--%>
<%@ include file="../header.jsp"%>

<%--회원가입 form--%>
<div class="container-fluid" style="margin-top: 20px;">
    <div class="col-lg-8"></div>
    <div class="col-lg-8" style="margin-left: auto; margin-right: auto;">
        <div class="jumbotron" style="padding-top: 20px;">
            <form method="post" action="../action/joinAction.jsp">
                <h3 style="text-align: center;">회원가입</h3>
                사용자 ID
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="ID" name="userID" maxlength="20">
                </div>
                비밀번호
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="Password" name="userPassword">
                </div>
                비밀번호 확인
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="Password" name="userPasswordChk">
                </div>
                이름
                <div class="form-group">
                    <input type="text" class="form-control" name="userName">
                </div>
                이메일
                <div class="form-group">
                    <input type="email" class="form-control" name="userEmail">
                </div>
                성별
                <div class="form-group" style="text-align: left">
                    <div class="btn-group" data-toggle="buttons">
                        <label class="btn btn-primary active">
                            <input type="radio" name="userGender" value="man" checked="checked"/>남
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="userGender" value="woman"/>여
                        </label>
                    </div>
                </div>

                <input type="submit" class="btn btn-primary form-control" value="회원가입">
            </form>
        </div>
    </div>
    <div class="col-lg-8"></div>
</div>


<%--footer--%>
<%@ include file="../footer.jsp"%>
