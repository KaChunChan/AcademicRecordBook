<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html lang="en" xmlns:form="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Admin Classes</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<form:form method="GET" action="/admin">
    <input type="submit" value="Go Back">
</form:form>

<h1>Add Class</h1>
<c:set var="error" value="${error}"/>
<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>
<form:form method="POST" action="/admin-classes-add-class" modelAttribute="classForm">
    <table>
        <tr>
            <td>
                <form:label path="code">Code</form:label>
                <form:input path="code"/>
                <br>
                <form:errors path="code"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="subject">Subject</form:label>
                <form:select path="subject">
                    <c:forEach items="${subjects}" var="subjectOption">
                        <form:option value="${subjectOption}" label="${subjectOption.code} ${subjectOption.title}"/>
                    </c:forEach>
                    <br>
                    <form:errors path="subject"/>
                </form:select>
            </td>
        </tr>
    </table>
    <input type="submit" value="Add Class"/>
</form:form>

<br>
<h1>All Classes</h1>
<table>
    <thead>
    <td>Code</td>
    <td>Title</td>
    </thead>
    <c:forEach items="${classes}" var="class">
        <tr>
            <td>
                ${class.code}
            </td>
            <td>
                ${class.subject.code} ${class.subject.title}
            </td>
            <td>
                <form method="get" action="/admin-view-class">
                    <input type="hidden" name="code" value="${class.code}"/>
                    <input type="submit" value="View"/>
                </form>
            </td>
            <td>
                <form method="get" action="/admin-edit-class">
                    <input type="hidden" name="code" value="${class.code}"/>
                    <input type="submit" value="Edit"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>