<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Add Test</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
    <style>body{background-color: black; color: red} input, select, button{background-color: #263238 !important; color: red !important;}</style>
</head>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<c:set var="text">
    <fmt:message key="createTest" />
</c:set>
<custom:header text="${text}"/>

<body>
<div class="container" style="min-height: 100vh; padding-top: 100px">
    <form action="${pageContext.request.contextPath}/app/save-test.html" method="post">
        <h3><fmt:message key="testName"/>: </h3>
        <div class="form-group">
            <input class="form-control" id="test-name" name="test-name" placeholder="<fmt:message key="testName"/>">
        </div>

        <h3><fmt:message key="questions"/>: </h3>
        <div id="questions">
            <div >
                <div class="form-group" style="margin-left: 0">
                    <input class="form-control" id="question-1" name="question-1" placeholder="<fmt:message key="question"/>">
                    <input class="form-control" id="answer-1" name="answer-1" placeholder="<fmt:message key="answer"/>">
                </div>
            </div>
        </div>

        <input class="btn btn-danger" type="button" onclick="onAddQuestion()"
               value="<fmt:message key="addQuestion"/>">
        <input class="btn btn-danger" type="submit" value="<fmt:message key="save"/>">
    </form>
</div>

<script>
    let questionNumber = 2;
    const questions = document.querySelector("#questions");

    const createQuestionFormGroup = () => {
        const formGroup = document.createElement("div");
        formGroup.innerHTML =
            `
                <div class="form-group" style="margin-left: 0">
                    <input class="form-control" id="question-` + questionNumber + `" name="question-` + questionNumber + `"  placeholder="<fmt:message key="question"/>">
                    <input class="form-control" id="answer-` + questionNumber + `" name="answer-` + questionNumber + `" placeholder="<fmt:message key="answer"/>">
                </div>

                <div class="form-group">
                    <input class="btn btn-danger" type="button" onclick="onRemoveQuestionGroup(` + questionNumber + `)" value="<fmt:message key="remove"/>">
                </div>
            `;
        formGroup.setAttribute("data-question-number", questionNumber);

        questions.appendChild(formGroup);
        questionNumber += 1;
    }

    const onAddQuestion = () => {
        createQuestionFormGroup();
    }

    const onRemoveQuestionGroup = (number) => {
        const nodeToRemove = Array.from(questions.children).find(q => q.dataset.questionNumber === number.toString());
        questions.removeChild(nodeToRemove);
    }
</script>


</body>
</html>
