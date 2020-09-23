<%--@elvariable id="sessions" type="java.util.List<com.wrox.chat.ChatSession>"--%>
<template:basic htmlTitle="Support Chat" bodyTitle="Support Chat Requests">
    <c:choose>
        <c:when test="${fn:length(sessions) == 0}">
            <i>대기중인 채팅 요청이 없습니다.</i>
        </c:when>
        <c:otherwise>
            채팅 요청을 클릭하여 수락하십시오.:<br /><br />
            <c:forEach items="${sessions}" var="s">
                <a href="javascript:void 0;"
                   onclick="join(${s.sessionId});">${s.customerUsername}</a><br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <script type="text/javascript" language="javascript">
        var join = function(id) {
            postInvisibleForm('<c:url value="/chat" />', {
                action: 'join', chatSessionId: id
            });
        };
    </script>
</template:basic>
