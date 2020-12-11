<%@ attribute name="text" required="true" type="java.lang.String" description="Header text" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag pageEncoding="UTF-8"%>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: red">
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <a class="navbar-brand" style="text-decoration: underline; color: black" href="${pageContext.request.contextPath}/app/list.html">${text}</a>
        </c:when>
        <c:otherwise>
            <a class="navbar-brand" style="text-decoration: underline; color: black" href="#">${text}</a>
        </c:otherwise>
    </c:choose>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ol class="breadcrumb ml-auto mr-4" style="background-color: #263238; " id="langs">
            <li class="breadcrumb-item"><a data-value="en" style="text-decoration: underline; color: red" href="#">En</a></li>
            <li class="breadcrumb-item"><a data-value="ru" style="text-decoration: underline; color: red" href="#">Ру</a></li>
        </ol>
        <c:if test="${not empty sessionScope.user}">
                <a class="btn btn-danger" style="background-color: #263238; color: red" href="${pageContext.request.contextPath}/app/signout.html" style="min-width: 150px">
                    <fmt:message key="signout"/>
                </a>
        </c:if>
    </div>

    <script>
        const langs = document.querySelector("#langs").children;

        Array.from(langs).map(lang => lang.children[0]).forEach(lang => {
            lang.addEventListener("click", async (e) => {
                e.preventDefault();

                await fetch("${pageContext.request.contextPath}/app/lang.html?value=" + lang.dataset.value);
                document.location.reload();
            });
        })
    </script>
</nav>
