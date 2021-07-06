<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
</body>
</html>