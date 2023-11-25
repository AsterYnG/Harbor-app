<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="order-section">
    <h2>Создать заказ</h2>
    <form action="/buttonClient" method="get">
        <button onclick="addOrder()" name="buttonCreateOrder" value="true" >Создать заказ</button>
    </form>
</section>

<section class="order-section">
    <h2>Текущие заказы</h2>
    <c:forEach var="order" items="${sessionScope.currentOrders}">
        <p>${order}</p>
    </c:forEach>
</section>

<section class="order-section">
    <h2>История заказов</h2>
    <c:forEach var="order" items="${sessionScope.orderHistory}">
        <p>${order}</p>
    </c:forEach>
</section>

