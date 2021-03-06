<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-24
  Time: 오후 6:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>My Web Application</title>

    <!-- Bootstrap core CSS -->
    <link href="/myWebApp_war/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/myWebApp_war/css/simple-sidebar.css" rel="stylesheet">

</head>

<body>
<div class="d-flex toggled" id="wrapper">

    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="sidebar-heading">MENU</div>
        <div class="list-group list-group-flush">
            <a href="/myWebApp_war/jsp/view/board.jsp" class="list-group-item list-group-item-action bg-light">게시판</a>
            <a href="#" class="list-group-item list-group-item-action bg-light">Shortcuts</a>
            <a href="#" class="list-group-item list-group-item-action bg-light">Overview</a>
            <a href="#" class="list-group-item list-group-item-action bg-light">Events</a>
            <a href="#" class="list-group-item list-group-item-action bg-light">Profile</a>
            <a href="#" class="list-group-item list-group-item-action bg-light">Status</a>
        </div>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

        <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
            <a class="navbar-brand" href="/myWebApp_war/index.jsp">메인페이지</a>

            <!--
                    <button class="btn btn-primary" id="menu-toggle">Menu</button>
            -->

            <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" id="menu-toggle">Menu</a>
                </li>
            </ul>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            접속하기
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                            <%
                                String userID = null;
                                if (session.getAttribute("userID") != null) {
                                    userID = (String) session.getAttribute("userID");
                            %>
                            <a class="dropdown-item" href="/myWebApp_war/jsp/action/logoutAction.jsp">로그아웃</a>
                            <%
                            } else {
                            %>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="/myWebApp_war/jsp/view/login.jsp">로그인</a>
                            <a class="dropdown-item" href="/myWebApp_war/jsp/view/join.jsp">회원가입</a>
                            <%
                                }
                            %>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
