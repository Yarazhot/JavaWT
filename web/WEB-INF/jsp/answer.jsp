<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Answers</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: black; color: red} input, button{background-color: #263238 !important; color: red !important;}</style>
</head>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<%--@elvariable id="test" type="com.duzh.denis.entities.Test"--%>
<c:set var="text">
    ${test.name}
</c:set>
<custom:header text="${text}"/>
<body>

<div class="container" style="min-height: 100vh; padding-top: 120px">
    <form action="${pageContext.request.contextPath}/app/save-answer.html?id=${test.id}" method="post">
        <c:forEach var="question" items="${test.questions}" varStatus="loop">
            <div class="form-group">
                <label for="question-${question.id}">${question.text}</label>
                <input class="form-control" id="question-${question.id}" name="question-${question.id}" class="input-block">
            </div>
        </c:forEach>

        <button class="btn btn-danger"><fmt:message key="submit"/></button>
    </form>
</div>

</body>
</html>
