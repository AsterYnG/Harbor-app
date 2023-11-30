<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${sessionScope.active == 'addOrder'}">
    <div id="newOrderFormModal" class="modal">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/client" method="post">
                <button value="true" name="exit" class="close-button">&#10006;</button>
            </form>
            <form id="newOrderForm" class="modal-form" action="/client" method="post">
                <h2>Создать заказ</h2>
                <label for="cargoWeight">Вес груза (в килограммах):</label>
                <input type="text" id="cargoWeight" name="cargoWeight" pattern="^\d+$" maxlength="4" required><br>

                <label for="cargoSize">Размер груза (в кубометрах):</label>
                <input type="text" id="cargoSize" name="cargoSize" pattern="^\d+$" maxlength="3" required><br>

                <label for="isFragile">Хрупкий груз:</label>
                <input type="checkbox" id="isFragile" name="isFragile"><br>

                <label for="destinationRoute">Город отправления:</label>
                <select id="destinationRoute" name="destinationRoute">
                    <c:forEach var="destinationCity" items="${sessionScope.destinations}">
                        <option value="${destinationCity.destinationCity}">${destinationCity.destinationCity}</option>
                    </c:forEach>
                </select><br>

                <div class="form-group">
                    <button type="submit">Выбрать перевозчика</button>
                </div>
            </form>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'addFreighterToOrder'}">
    <div id="selectFreighterModal" class="modal">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/client" method="post">
                <button value="true" name="exit" class="close-button">&#10006;</button>
            </form>
            <form id="selectFreighterForm" class="modal-form" action="/client" method="post">
                <h2>Выбрать перевозчика</h2>
                <label for="freighterName">Перевозчики:</label>
                <select id="freighterName" name="freighterName">
                    <c:forEach var="freighter" items="${sessionScope.availableFreighters}">
                        <option value="${freighter.freighterName}">Перевозчик: ${freighter.freighterName}, Цена: ${freighter.shippingPrice}</option>
                    </c:forEach>
                </select><br>

                <div class="form-group">
                    <button type="submit">Добавить заказ</button>
                </div>
            </form>
        </div>
    </div>
</c:if>