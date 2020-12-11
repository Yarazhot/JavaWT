<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Students</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: black; color: red}</style>
</head>

<body>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<c:set var="text">
    <fmt:message key="students"/>
</c:set>
<custom:header text="${text}"/>

<div class="container" style="min-height: 100vh; padding-top: 120px">
    <table  class="table table-dark" style="background-color: #282c30; color: red">
        <thead>
        <tr>
            <th>#</th>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="answer"/></th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="students" type="java.util.List<entity.UserEntity>"--%>
        <%--@elvariable id="testId" type="java.lang.String"--%>
        <c:forEach var="student" items="${students}" varStatus="loop">
            <tr>
                <td>${loop.index + 1}</td>
                <td>${student.name} </td>
                <td>
                    <a style="color: red; text-decoration: underline" href="${pageContext.request.contextPath}/app/details.html?id=${testId}&studentId=${student.id}">
                        <fmt:message key="answer"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
