<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-24
  Time: 오전 2:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Simple Sidebar - Start Bootstrap Template</title>

    <!-- Bootstrap core CSS -->
    <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../../css/simple-sidebar.css" rel="stylesheet">

</head>

<body>
<%
    String userID = null;
    if(session.getAttribute("userID") !=null){
        userID = (String) session.getAttribute("userID");
    }
    if(userID != null){
        PrintWriter writer = response.getWriter();
        writer.println("<script>");
        writer.println("alert('이미 로그인 되어 있습니다.')");
        writer.println("location.href='../../index.html'");
        writer.println("</script>");
    }

%>
<div class="d-flex toggled" id="wrapper">

    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="sidebar-heading">MENU</div>
        <div class="list-group list-group-flush">
            <a href="board.jsp" class="list-group-item list-group-item-action bg-light">게시판</a>
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
            <a class="navbar-brand" href="../../index.html">메인페이지</a>

            <!--
                    <button class="btn btn-primary" id="menu-toggle">Menu</button>
            -->

            <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" id="menu-toggle">Menu</a>
                </li>
            </ul>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
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
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            접속하기
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="login.jsp">로그인</a>
                            <a class="dropdown-item" href="join.jsp">회원가입</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#">Something else here</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container-fluid" style="margin-top: 20px;">
            <div class="col-lg-4"></div>
            <div class="col-lg-4" style="margin-left: auto; margin-right: auto;">
                <div class="jumbotron" style="padding-top: 20px;">
                    <form method="post" action="../action/loginAction.jsp">
                        <h3 style="text-align: center;">로그인</h3>
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="ID" name="userID" maxlength="20">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" placeholder="Password" name="userPassword">
                        </div>
                        <input type="submit" class="btn btn-primary form-control" value="로그인">
                    </form>
                </div>
            </div>
            <div class="col-lg-4"></div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>
<!-- /#wrapper -->

<!-- Bootstrap core JavaScript -->
<script src="../../vendor/jquery/jquery.min.js"></script>
<script src="../../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Menu Toggle Script -->
<script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>

</body>

</html>
