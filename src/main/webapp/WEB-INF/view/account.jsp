<!DOCTYPE html>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <p>Hello ${endUser.forename} ${endUser.surname}</p>
    <p>Your username is ${endUser.username}</p>
    <p>Your password is ${endUser.password}</p>
    <p>Your email is ${endUser.email}</p>
    <p>Your are logged in as ${endUser.role}</p>

    <c:if test="${endUser.role=='ADMIN'}">
        <a href="/admin"> <button>Admin</button> </a>
    </c:if>
</body>
</html>