<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Details</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: black; color: red}</style>

</head>
<body>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<%--@elvariable id="test" type="entity.TestEntity"--%>
<c:set var="text">
    ${test.name}
</c:set>
<custom:header text="${text}"/>

<div class="container" style="min-height: 100vh; padding-top: 120px">
    <ul class="list-group" style="background-color: #282c30">
        <c:forEach var="question" items="${test.questions}" varStatus="loop">
            <li class="list-group-item" style="background-color: #282c30">
                    ${question.text}
                    <%--@elvariable id="answers" type="java.util.List<entity.AnswerEntity>"--%>
                <c:if test="${not empty answers}">
                    <ul class="list-group" style="background-color: #282c30">
                        <c:choose>
                            <c:when test="${answers[loop.index].text eq question.correctAnswer}">
                                <li class="list-group-item" style="background-color: #282c30">${answers[loop.index].text}</li>
                            </c:when>

                            <c:otherwise>
                                <li class="list-group-item" style="background-color: #282c30">${answers[loop.index].text}</li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </c:if>
            </li>
        </c:forEach>
    </ul>
</div>

</body>
</html>
