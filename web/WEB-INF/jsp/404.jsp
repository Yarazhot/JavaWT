<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>404 Error</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">

</head>
<body>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<c:set var="text">
    404
</c:set>
<custom:header text="${text}"/>

<div class="container"  style="min-height: 100vh; padding-top: 120px; color: red; text-align: center">
    <h1>
        <fmt:message key="error404"/>
    </h1>
</div>

</body>
</html>
