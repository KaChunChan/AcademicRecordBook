<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<p>Admin</p>
<form:form method="GET" action="/admin-add-user">
    <input type="submit" value="Add New User">
</form:form>

<form:form method="GET" action="/">
    <input type="submit" value="Back To Account">
</form:form>
<table>
    <thead>
    <td>Forename</td>
    <td>Surname</td>
    <td>Username</td>
    <td>E-mail</td>
    <td>Password</td>
    <td>Role</td>
    </thead>
    <c:forEach items="${accounts}" var="account">
        <tr>
            <td>
                "${account.forename}"
            </td>
            <td>
                "${account.surname}"
            </td>
            <td>
                "${account.username}"
            </td>
            <td>
                "${account.email}"
            </td>
            <td>
                "${account.password}"
            </td>
            <td>
                "${account.role}"
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>