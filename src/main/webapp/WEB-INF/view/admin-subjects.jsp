<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Subjects</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<p>Subjects</p>

<c:set var="error" value="${error}"/>
<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form:form method="POST" action="/admin-subjects-add-subject" modelAttribute="subjectForm">
    <table>
        <tr>
            <td>
                <form:label path="code">Code</form:label>
                <form:input path="code"/>
                <br>
                <form:errors path="code"/>
            </td>
        </tr>
        </tr>
        <td>
            <form:label path="title">Title</form:label>
            <form:input path="title"/>
            <br>
            <form:errors path="title"/>
        </td>
        </tr>
    </table>
    <input type="submit" value="Add Subject"/>
</form:form>
<form:form method="GET" action="/admin">
    <input type="submit" value="Cancel">
</form:form>

<table>
    <thead>
    <td>Code</td>
    <td>Title</td>
    </thead>
    <c:forEach items="${subjects}" var="subject">
        <tr>
            <td>
                "${subject.code}"
            </td>
            <td>
                "${subject.title}"
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>