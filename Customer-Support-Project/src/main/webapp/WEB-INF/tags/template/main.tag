
<%--본문 콘텐츠 포함 가능, 스크립틀릿, 식 제외하고 동적 속성 지원 / 공백 제거--%>

<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>

<%--속성 설정--%>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="headContent" fragment="true" required="false" %>
<%@ attribute name="navigationContent" fragment="true" required="true" %>

<%--base.jspf 파일을 포함--%>
<%@ include file="/WEB-INF/jsp/base.jspf" %>


<!DOCTYPE html>
<html>

<head>
    <title>Customer Support :: <c:out value="${fn:trim(htmlTitle)}" /> </title>
    <link rel="stylesheet" href="<c:url value="/resource/stylesheet/main.css" />">
    <jsp:invoke fragment="headContent" />
</head>

<body>
    <h1>Multinational Widget Corporation</h1>
    <table border="0" id="bodyTable">
        <tbody>
            <tr>
                <td class="sidebarCell">
                    <jsp:invoke fragment="navigationContent" />
                </td>
                <td class="contentCell">
                    <h2><c:out value="${fn:trim(bodyTitle)}" /></h2>
                    <jsp:doBody />
                </td>
            </tr>
        </tbody>
    </table>

</body>
</html>
