<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="order-section">
    <h2>Создать заказ</h2>
    <form action="/buttonClient" method="get">
        <button name="buttonCreateOrder" value="true" >Создать заказ</button>
    </form>
</section>

<section class="order-section" id="currentOrdersSection">
    <h2>Текущие заказы</h2>
    <div id="currentOrdersContainer">
        <c:forEach var="order" items="${sessionScope.currentOrders}" varStatus="loop">
            <c:if test="${loop.index < 3 or sessionScope.showAllCurrentOrders}">
                <p>${order}</p>
            </c:if>
        </c:forEach>
    </div>
    <c:if test="${sessionScope.currentOrders.size() > 3}">
        <button onclick="showAllCurrentOrders()">Показать все</button>
    </c:if>
</section>

<script>
    var showAllCurrentOrders = function() {
        var currentOrdersContainer = document.getElementById("currentOrdersContainer");
        currentOrdersContainer.innerHTML = `<c:forEach var="order" items="${sessionScope.currentOrders}">
                                                <p>${order}</p>
                                            </c:forEach>`;

        // Убрать кнопку "Показать все"
        document.querySelector("#currentOrdersSection button").style.display = "none";
    };
</script>

<section class="order-section" id="orderHistorySection">
    <h2>История заказов</h2>
    <div id="orderHistoryContainer">
        <c:forEach var="order" items="${sessionScope.orderHistory}" varStatus="loop">
            <c:if test="${loop.index < 3 or sessionScope.showAllOrderHistory}">
                <p>${order}</p>
            </c:if>
        </c:forEach>
    </div>
    <c:if test="${sessionScope.orderHistory.size() > 3}">
        <button onclick="showAllOrderHistory()">Показать все</button>
    </c:if>
</section>

<script>
    var showAllOrderHistory = function() {
        var orderHistoryContainer = document.getElementById("orderHistoryContainer");
        orderHistoryContainer.innerHTML = `<c:forEach var="order" items="${sessionScope.orderHistory}">
                                                <p>${order}</p>
                                            </c:forEach>`;

        // Убрать кнопку "Показать все"
        document.querySelector("#orderHistorySection button").style.display = "none";
    };
</script>

