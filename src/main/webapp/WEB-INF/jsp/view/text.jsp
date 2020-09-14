<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-15
  Time: 오전 4:27
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="shortText" type="java.lang.String"--%>
<%--@elvariable id="longText" type="java.lang.String"--%>


<template:main htmlTitle="Abbreviating Text">
    <b>Short text:</b> ${dft:abbreviateString(shortText, 32)}<br />
    <b>Long text:</b> ${dft:abbreviateString(longText, 32)}<br />
</template:main>
