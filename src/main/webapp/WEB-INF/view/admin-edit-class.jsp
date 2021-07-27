<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Class</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<form method="get" action="/admin-classes">
    <input type="submit" value="Go Back">
</form>

<h1>Edit Class</h1>
<c:set var="error" value="${error}"/>
<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form:form method="post" action="/admin-update-class" modelAttribute="classForm">
    <table>
        <tr>
            <td>
                <form:label path="code">Code ID</form:label>
                <form:input path="code"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="subject">Subject</form:label>
                <form:input path="subject"/>
            </td>
        </tr>
        <td>
            <form:label path="instructor">Instructor</form:label>
            <form:input path=""/>
        </td>
        </tr>
        <tr>
            <td>
                <c:forEach items="${availableRoles}" var="availableRole">
                    ${availableRole}
                    <form:radiobutton path="role" value="${availableRole}"/>
                </c:forEach>
            </td>
        </tr>
    </table>
    <input type="hidden" name="classCode" value="${classForm.code}"/>
    <input type="submit" value="Submit"/>
</form:form>
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