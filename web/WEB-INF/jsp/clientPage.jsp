<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Страница клиента морского порта</title>
    <style>
        <%@include file="css/clientPage.css"%>
    </style>
</head>
<header>
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <button class="logout-btn">Выход</button>
    </form>
    <form action="${pageContext.request.contextPath}/buttonClient" method="get">
        <button class="search-btn" name="buttonSearchOrders" value="true">Поиск</button>
    </form>
</header>

<body>
<jsp:include page="profileSection.jsp"/>

<jsp:include page="changePasswordModal.jsp"/>

<jsp:include page="orderSection.jsp" />

<jsp:include page="create-order-modal.jsp" />

<c:if test="${sessionScope.active == 'showOrderSearch'}">
    <div id="ordersFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/client" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="ordersSearchForm" class="modal-form" action="${pageContext.request.contextPath}/client" method="post">
            <div class="error-message" id="login-error">${requestScope.error.message}</div>
            <h2>Поиск по заказам</h2>

            <h3>Id:</h3>
            <div class="form-group">
                <label for="orderIdByClient">Id заказа:</label>
                <input type="number" id="orderIdByClient" name="orderIdByClient" pattern="^(\S|\d)+$" max="10000">
            </div>

            <h3>Дата заказа:</h3>
            <div class="form-group">
                <label for="orderDateFromByClient">Дата от:</label>
                <input type="datetime-local" id="orderDateFromByClient" name="orderDateFromByClient" min="2000-01-01" max="2023-12-02">
            </div>
            <div class="form-group">
                <label for="orderDateToByClient">Дата от:</label>
                <input type="datetime-local" id="orderDateToByClient" name="orderDateToByClient" min="2000-01-01" max="2023-12-02">
            </div>
            <h3>Статус заказа:</h3>
            <c:forEach var="status" items="${sessionScope.statusList}">
                <div class="form-group">
                    <label for="${status}">${status}:</label>
                    <input type="checkbox" id="${status}" name="${status}"
                           value="true">
                </div>
            </c:forEach>


            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showSearchResultOrdersForCustomer'}">
    <div id="SearchResultClientsModal" class="modal">
        <form action="${pageContext.request.contextPath}/client" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="selectFreighterForm" class="modal-form">
            <c:forEach var="order" items="${sessionScope.searchResult}">
                <p>${order}</p>
            </c:forEach>
        </form>
    </div>
</c:if>

</body>
</html>