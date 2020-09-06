<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-06
  Time: 오후 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    // 인스턴스 변수 정의
    int[] arr = new int[]{6, 4, 2, 5, 1, 3, 7};

    // 메서드 정의
    public int returnFirst(int[] arr) {
        return arr[0];
    }
%>
<%
    int beforeSort = returnFirst(arr);
    for(int i=0; i<arr.length; i++){
        for(int j=i+1; j<arr.length; j++){
            if(arr[i]>arr[j]){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
    int afterSort = returnFirst(arr);
%>
<html>
<head>
    <title>스크립트 요소를 사용하는 방법</title>
</head>
<body>
<h1>스크립트 요소 테스트</h1>
정렬 전 첫번째 값 : <%=beforeSort%><br>
정렬 후 첫번째 값 : <%=afterSort%>
</body>
</html>

