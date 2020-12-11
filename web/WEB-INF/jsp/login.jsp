<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: black; color: red} input, select, button{background-color: #263238 !important; color: red !important;}</style>
</head>

<body>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<c:set var="text">
    <fmt:message key="signin"/>
</c:set>
<custom:header text="${text}"/>

<div class="container" style="min-height: 100vh; padding-top: 120px">
    <div>
        <form action="${pageContext.request.contextPath}/app/login.html" method="post">
            <div class="form-group">
                <label for="email"><fmt:message key="email"/></label>
                <input class="form-control" id="email" name="email" placeholder="<fmt:message key="email"/>">
            </div>

            <div class="form-group">
                <label for="password"><fmt:message key="password"/></label>
                <input class="form-control" id="password" name="password" placeholder="<fmt:message key="password"/>">
            </div>

            <div class="form-group">
                <button class="btn btn-danger"><fmt:message key="submit"/></button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
