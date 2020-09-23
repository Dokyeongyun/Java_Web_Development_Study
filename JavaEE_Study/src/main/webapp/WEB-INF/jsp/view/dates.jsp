<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2020-09-15
  Time: 오전 4:14
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="date" type="java.util.Date"--%>
<%--@elvariable id="calendar" type="java.util.Calendar"--%>
<%--@elvariable id="instant" type="java.time.Instant"--%>
<template:main htmlTitle="Displaying Dates Properly">
    <b>Date:</b>
    <dft:formatDate value="${date}" type="both" dateStyle="full"
                     timeStyle="full" /><br />
    <b>Date, time first with separator:</b>
    <dft:formatDate value="${date}" type="both" dateStyle="full"
                     timeStyle="full" timeFirst="true"
                     separateDateTimeWith=" on " /><br />
    <b>Calendar:</b>
    <dft:formatDate value="${calendar}" type="both" dateStyle="full"
                     timeStyle="full" /><br />
    <b>Instant:</b>
    <dft:formatDate value="${instant}" type="both" dateStyle="full"
                     timeStyle="full" /><br />
    <b>Instant, time first with separator:</b>
    <dft:formatDate value="${instant}" type="both" dateStyle="full"
                     timeStyle="full" timeFirst="true"
                     separateDateTimeWith=" on " /><br />
</template:main>
