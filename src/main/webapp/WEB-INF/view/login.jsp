<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<div>
    <h1> My Custom Login Page </h1>
    <form method="POST" action="/login">
        <c:if test="${param.error == 'true'}">
            <p style='color:red'>
                Invalid credentials!
            </p>
        </c:if>
        <c:if test="${param.logout == 'true'}">
            <p style='color:blue'>
                Logged out successfully.
            </p>
        </c:if>
        Username: <input type="text" name="username"/>
        <br>
        Password: <input type="password" name="password"/>
        <br>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <button type="submit">Log in</button>
    </form>
</div>
</body>
</html>