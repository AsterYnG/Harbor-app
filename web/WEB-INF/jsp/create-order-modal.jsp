<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${sessionScope.active == 'addOrder'}">
    <div id="newOrderFormModal" class="modal">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/client" method="post">
                <button value="true" name="exit" class="close-button">&#10006;</button>
            </form>
            <form id="newOrderForm" class="modal-form" action="${pageContext.request.contextPath}/client" method="post">
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
            <form id="selectFreighterForm" class="modal-form" action="${pageContext.request.contextPath}/client" method="post">
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
<script>
    console.log("${sessionScope.active}");
</script>
<c:if test="${sessionScope.active == 'changeOrder'}">
    <div id="changeOrderFormModal" class="modal">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/client" method="post">
                <button value="true" name="exit" class="close-button">&#10006;</button>
            </form>
            <form id="chooseOrderForm" class="modal-form" action="${pageContext.request.contextPath}/client" method="post">
                <h2>Выбрать заказ</h2>
                <label for="clientCurrentOrders">Ваши текущие заказы:</label>
                <select id="clientCurrentOrders" name="clientCurrentOrders">
                    <c:forEach var="clientsOrder" items="${sessionScope.currentOrdersToChange}">
                        <option value="${clientsOrder.orderId}">${clientsOrder}</option>
                    </c:forEach>
                </select><br>

                <div class="form-group">
                    <button type="submit">Изменить заказ</button>
                </div>
            </form>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'changeOrderCharacteristics'}">
    <div id="changeOrderCharacteristicsFormModal" class="modal">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/client" method="post">
                <button value="true" name="exit" class="close-button">&#10006;</button>
            </form>
            <form id="changeOrderForm" class="modal-form" action="${pageContext.request.contextPath}/client" method="post">
                <h2>Изменить заказ</h2>
                <label for="correctedCargoWeight">Вес груза (в килограммах):</label>
                <input type="text" id="correctedCargoWeight" name="correctedCargoWeight" value="${sessionScope.currentWeight}" pattern="^\d+$" maxlength="4" required><br>

                <label for="correctedCargoSize">Размер груза (в кубометрах):</label>
                <input type="text" id="correctedCargoSize" name="correctedCargoSize" value="${sessionScope.currentSize}" pattern="^\d+$" maxlength="3" required><br>

                <label for="correctedIsFragile">Хрупкий груз:</label>
                <input type="checkbox" id="correctedIsFragile" name="correctedIsFragile" ${sessionScope.currentIsFragile ? 'checked' : ''}><br>

                <label for="correctedDestinationRoute">Город отправления:</label>
                <select id="correctedDestinationRoute" name="correctedDestinationRoute">
                    <c:forEach var="correctedDestinationCity" items="${sessionScope.destinations}">
                        <option value="${correctedDestinationCity.destinationCity}"
                                <c:if test="${not empty sessionScope.destinationCity and sessionScope.destinationCity eq correctedDestinationCity.destinationCity}">
                                    selected="selected"
                                </c:if>
                        >${correctedDestinationCity.destinationCity}</option>
                    </c:forEach>
                </select><br>

                <div class="form-group">
                    <button type="submit">Выбрать перевозчика</button>
                </div>
            </form>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'addFreighterToChangedOrder'}">
    <div id="selectFreighterChangedModal" class="modal">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/client" method="post">
                <button value="true" name="exit" class="close-button">&#10006;</button>
            </form>
            <form id="selectFreighterFormChanged" class="modal-form" action="${pageContext.request.contextPath}/client" method="post">
                <h2>Выбрать перевозчика</h2>
                <label for="correctedFreighterName">Перевозчики:</label>
                <select id="correctedFreighterName" name="correctedFreighterName">
                    <c:forEach var="freighter" items="${sessionScope.newAvailableFreighters}">
                        <option value="${freighter.freighterName}">Перевозчик: ${freighter.freighterName}, Цена: ${freighter.shippingPrice}</option>
                    </c:forEach>
                </select><br>

                <div class="form-group">
                    <button type="submit">Принять изменения</button>
                </div>
            </form>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'removeOrder'}">
    <div id="removeOrderFormModal" class="modal">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/client" method="post">
                <button value="true" name="exit" class="close-button">&#10006;</button>
            </form>
            <form id="removeOrderForm" class="modal-form" action="${pageContext.request.contextPath}/client" method="post">
                <h2>Выбрать заказ</h2>
                <label for="clientCurrentOrdersToRemove">Ваши текущие заказы:</label>
                <select id="clientCurrentOrdersToRemove" name="clientCurrentOrdersToRemove">
                    <c:forEach var="clientsOrderToRemove" items="${sessionScope.currentOrdersToRemove}">
                        <option value="${clientsOrderToRemove.orderId}">${clientsOrderToRemove}</option>
                    </c:forEach>
                </select><br>

                <div class="form-group">
                    <button type="submit">Удалить заказ</button>
                </div>
            </form>
        </div>
    </div>
</c:if>