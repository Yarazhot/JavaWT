<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: black; color: red} input, select, button{background-color: #263238 !important; color: red !important;}</style>
</head>
<body>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<c:set var="text">
    <fmt:message key="signup"/>
</c:set>
<custom:header text="${text}"/>

<div class="container" style="min-height: 100vh; padding-top: 120px">
    <form action="${pageContext.request.contextPath}/app/registration.html" method="post">
        <div class="form-group">
            <label for="name"><fmt:message key="name"/></label>
            <input class="form-control" id="name" name="name" placeholder="<fmt:message key="name"/>">
        </div>

        <div class="form-group">
            <label for="email"><fmt:message key="email"/></label>
            <input class="form-control" id="email" name="email" placeholder="<fmt:message key="email"/>">
        </div>

        <div class="form-group">
            <label for="password"><fmt:message key="password"/></label>
            <input class="form-control" id="password" name="password" placeholder="<fmt:message key="password"/>">
        </div>

        <div class="form-group">
            <label for="role"><fmt:message key="role"/></label>
            <select class="form-control" id="role" name="role">
                <option value="Student">
                    <fmt:message key="student"/>
                </option>

                <option value="Teacher">
                    <fmt:message key="teacher"/>
                </option>
            </select>
        </div>

        <div>
            <button class="btn btn-danger"><fmt:message key="submit"/></button>
        </div>
    </form>
</div>

</body>
</html>