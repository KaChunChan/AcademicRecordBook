<!DOCTYPE html>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <p>Hello ${account.forename} ${account.surname}</p>
    <p>Your username is ${account.username}</p>
    <p>Your password is ${account.password}</p>
    <p>Your email is ${account.email}</p>
    <p>Your are logged in as ${account.role}</p>

    <c:if test="${account.role=='ADMINISTRATOR'}">
        <a href="/admin"> <button>Admin</button> </a>
    </c:if>
</body>
</html>