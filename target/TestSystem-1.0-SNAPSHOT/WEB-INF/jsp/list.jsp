<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Test List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: black; color: red} button{background-color: #263238 !important; color: red !important;}</style>

</head>
<body>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<c:choose>
    <c:when test="${sessionScope.user.role == 'Teacher'}">
        <c:set var="text">
            <fmt:message key="createdTests"/>
        </c:set>
    </c:when>

    <c:otherwise>
        <c:set var="text">
            <fmt:message key="completeTests"/>
        </c:set>
    </c:otherwise>
</c:choose>

<custom:header text="${text}"/>

<div class="container" style="min-height: 100vh; padding-top: 120px">
    <div>
        <c:if test="${sessionScope.user.role == 'Teacher'}">
            <a class="btn btn-danger" style="text-align: center; background-color: #263238; color: red" href="${pageContext.request.contextPath}/app/add.html">
                <fmt:message key="newTest"/>
            </a>
        </c:if>
    </div>

    <table class="table table-dark" style="background-color: #282c30">
        <thead>
        <tr>
            <th style="width: 10%; color: red">#</th>
            <th style="width: 65%; color: red"><fmt:message key="testLink"/></th>

            <c:if test="${sessionScope.user.role == 'Teacher'}">
                <th style="width: 15%; color: red"><fmt:message key="viewStudents"/></th>
                <th style="width: 10%; color: red"><fmt:message key="deleteTest"/></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="tests" type="java.util.List<entity.TestEntity>"--%>
        <c:forEach var="test" items="${tests}" varStatus="loop">
            <tr>
                <td style="vertical-align: baseline; color: red">${loop.index + 1}</td>
                <td style="vertical-align: baseline">
                    <c:choose>
                        <c:when test="${sessionScope.user.role == 'Teacher'}">
                            <a style="text-decoration: underline; color: red" href="${pageContext.request.contextPath}/app/details.html?id=${test.id}">
                                    ${test.name}
                            </a>
                        </c:when>

                        <c:otherwise>
                            <c:set var="taken" value="false"/>
                            <%--@elvariable id="completeTests" type="java.util.List<entity.TestEntity>"--%>
                            <c:forEach var="takenTest" items="${completeTests}">
                                <c:if test="${test.id eq takenTest.id}">
                                    <c:set var="taken" value="true"/>
                                </c:if>
                            </c:forEach>

                            <c:choose>

                                <%--@elvariable id="completeTests" type="java.util.List<entity.TestEntity>"--%>
                                <c:when test="${taken}">
                                    <a style="text-decoration: underline; color: red" href="${pageContext.request.contextPath}/app/details.html?id=${test.id}&studentId=${sessionScope.user.id}">
                                        <fmt:message key="view"/> ${test.name}
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a style="text-decoration: underline; color: red" href="${pageContext.request.contextPath}/app/answer.html?id=${test.id}">
                                        <fmt:message key="answer"/> ${test.name}
                                    </a>
                                </c:otherwise>
                            </c:choose>

                        </c:otherwise>
                    </c:choose>
                </td>

                <c:if test="${sessionScope.user.role == 'Teacher'}">
                    <td style="vertical-align: baseline">
                        <a style="text-decoration: underline; color: red" href="${pageContext.request.contextPath}/app/students.html?id=${test.id}">
                            <fmt:message key="students"/>
                        </a>
                    </td>
                    <td style="vertical-align: baseline">
                        <a style="text-decoration: underline; color: red" href="${pageContext.request.contextPath}/app/delete-test.html?id=${test.id}">
                            <fmt:message key="delete"/>
                        </a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

</body>
</html>
