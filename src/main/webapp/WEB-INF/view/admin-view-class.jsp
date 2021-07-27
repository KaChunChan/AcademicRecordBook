<!DOCTYPE html>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Class</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<form method="get" action="/admin-classes">
    <input type="submit" value="Go Back">
</form>
<table>
    <thead>
    <td>Code</td>
    <td>Subject</td>
    <td>Instructor</td>
    </thead>
    <tr>
        <td>${class.code}</td>
        <td>${class.subject.code} ${class.subject.title}</td>
        <td>${class.instructor}</td>
    </tr>
</table>
<table>
    <thead>
    Students
    </thead>
    <c:forEach items="class.students" var="student">
        <tr>
            <td> ${student}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>