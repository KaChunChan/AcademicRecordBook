<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Add User</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <p>Admin Add User</p>
    <form:form method="post" action="/admin-add-user" modelAttribute="account">
        <table>
            <tr>
                <td>
                    <form:label path="forename">forename</form:label>
                    <form:input path="forename" />
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="surname">surname</form:label>
                    <form:input path="surname" />
                </td>
            </tr>
                <td>
                    <form:label path="username">username</form:label>
                    <form:input path="username" />
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="email">email</form:label>
                    <form:input path="email" type="email"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="password">password</form:label>
                    <form:input path="password" type="password"/>
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
        <input type="submit" value="Submit" />
    </form:form>
    <a href="/admin">
      <button>Cancel</button>
    </a>
</body>
</html>