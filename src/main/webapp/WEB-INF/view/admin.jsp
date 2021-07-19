<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en" xmlns:form="http://www.w3.org/1999/html">
<head>
  <meta charset="UTF-8">
  <title>Admin</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<c:set var="error" value="${error}"/>
<c:if test="${not empty error}">
<p>${error}</p>
</c:if>

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
      <td>
        <form method="get" action="/admin-edit-user">
          <input type="hidden" name="accountID" value="${account.id}"/>
          <input type="submit" value="Edit" >
        </form>
      </td>
      <td>
        <form method="post" action="/admin-delete-user">
          <input type="hidden" name="accountID" value="${account.id}"/>
          <input type="submit" value="Delete" >
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>