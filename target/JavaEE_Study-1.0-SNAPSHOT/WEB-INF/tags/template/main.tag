
<%--본문 콘텐츠 포함 가능, 스크립틀릿, 식 제외하고 동적 속성 지원 / dynamicAttributes 변수로 동적속성 접근, 공백 제거--%>
<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes" trimDirectiveWhitespaces="true" %>

<%--htmlTitle이라는 속성 설정--%>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>

<%--base.jspf 파일을 포함--%>
<%@ include file="/WEB-INF/jsp/base.jspf" %>


<!DOCTYPE html>
<html<c:forEach items="${dynamicAttributes}" var="a">
    <c:out value='${a.key}="${fn:escapeXml(a.value)}"' escapeXml="false" />
</c:forEach>>

<head>
    <title><c:out value="${fn:trim(htmlTitle)}" /> </title>
</head>

<body>

<%--doBody 태그는 태그 파일안에서만 사용 가능, JSP 태그 호출의 콘텐츠 평가 후 인라인으로 포함 or 변수에 저장--%>
    <jsp:doBody />
</body>