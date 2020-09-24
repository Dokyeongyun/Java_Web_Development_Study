<%--
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

<%--게시판 목록--%>
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light border-bottom">
        <ul class="navbar-nav mt-2 mt-lg-0">
            <li style="padding-left: 50px;"><a href="#"></a> 글 목록1</li>
            <li style="padding-left: 50px;"><a href="#"></a> 글 목록2</li>
            <li style="padding-left: 50px;"><a href="#"></a> 글 목록3</li>
            <li style="padding-left: 50px;"><a href="#"></a> 글 목록4</li>
        </ul>
    </nav>

    <h1 style="margin-top: 20px; margin-bottom: 20px" >게시판 이름</h1>
    <div class="table-responsive-lg">
    <table class="table table-hover table-striped">
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일자</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1</td>
            <td>게시판테스트</td>
            <td>도경윤</td>
            <td>2020-09-25</td>
            </tr>
        <tr>
            <td>2</td>
            <td>배고프다</td>
            <td>DKY</td>
            <td>2020-09-25</td>
        </tr>
        </tbody>
    </table>
    </div>
    <div align="right">
        <button class="btn btn-warning" type="button" onclick="location.href='writePost.jsp' ">글 쓰기</button>
    </div>
</div>


<%--footer--%>
<%@ include file="../footer.jsp" %>