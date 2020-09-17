<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Hello, World Application Index File</title>
  </head>
  <body>
  <a href="test">서블릿 테스트 페이지로 이동</a><br>
  <a href="FileUploadTest">파일 업로드 테스트 페이지로 이동</a><br>
  <a href="shop">세션 테스트 페이지로 이동</a><br>
  <a href="do/">세션객체의 메서드 테스트 페이지로 이동</a><br>
  <a href="profile">[EL] 암시적 EL 범위 사용 테스트 페이지로 이동</a><br>
  <a href="scope.jsp">[EL] 암시적 EL 범위의 우선순위를 확인하는 페이지로 이동</a><br>
  <a href="info.jsp?user=Do&colors=red&colors=black">[EL] 11가지 EL 내장 객체 사용 테스트 페이지로 이동</a><br>
  <a href="collections.jsp">[EL] 스트림 API를 이용한 컬렉션 접근 테스트 페이지로 이동</a><br>
  <a href="list">[JSTL] 코어 태그 라이브러리를 이용한 연락처 표시 테스트 페이지로 이동</a><br>
  <a href="list_internalization">[JSTL] 국제화 및 지역화 태그 라이브러리를 이용한 연락처 표시 테스트 페이지로 이동</a><br>
  <a href="index">[CustomTag] 커스텀 JSP 태그 테스트 페이지로 이동</a><br>
  <a href="index?dates">[CustomTag] 커스텀 JSP 날짜 서식 지정 태그 테스트 페이지로 이동</a><br>
  <a href="index?text">[CustomTag] abbreviate 메서드를 EL함수로 만들어 JSP에서 쉽게 사용하는 테스트 페이지로 이동</a><br>
  <a href="CompressionFilter">[Servlet Filter] CompressionFilter와 LoggingFilter 테스트 페이지로 이동</a><br>

  <%--JSTL의 코어 태그 라이브러리 <c:redirect> 를 이용한 리디렉션--%>
<%--
  <c:redirect url="/list_internalization"/>
--%>
  </body>
</html>
