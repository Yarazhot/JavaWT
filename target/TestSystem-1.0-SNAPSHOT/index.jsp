<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
  <head>
    <title>Test System</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
  </head>
  <body style="background-color: black">
  <fmt:setLocale value="${sessionScope.lang}"/>
  <fmt:setBundle basename="text"/>

  <c:set var="text">
    <fmt:message key="welcome"/>
  </c:set>

  <custom:header text="${text}"/>
  <div class="container " style="min-height: 100vh; padding-top: 220px">
    <h3 class="text-center" style="color: red">
      <fmt:message key="please"/>
      <a style="text-decoration: underline; color: red" href="${pageContext.request.contextPath}/app/login.html"><fmt:message key="signin"/></a>
      <fmt:message key="or"/>
      <a style="text-decoration: underline; color: red" href="${pageContext.request.contextPath}/app/registration.html"><fmt:message key="signup"/></a>
    </h3>
  </div>
  </body>
</html>
