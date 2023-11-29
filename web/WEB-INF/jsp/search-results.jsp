<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${sessionScope.active == 'showSearchResultAvailableRoutes'}">
    <div id="SearchResultAvailableRoutesModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>Страна назначения</th>
                    <th>Город назначения</th>
                    <th>Длительность в часах</th>
                    <th>Название перевозчика</th>
                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="route" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${route.route.destinationCountry}</td>
                        <td>${route.route.destinationCity}</td>
                        <td>${route.route.duration}</td>
                        <td>${route.freighter.freighterName}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'showSearchResultCargos'}">
    <div id="SearchResultCargosModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>ФИО клиента</th>
                    <th>Перевозчик</th>
                    <th>Вес груза</th>
                    <th>Размер груза</th>
                    <th>Хрупкость груза</th>
                    <th>Страна назначения</th>

                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="cargo" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${cargo.customer.fullName}</td>
                        <td>${cargo.freighter.freighterName}</td>
                        <td>${cargo.cargoWeight}</td>
                        <td>${cargo.cargoSize}</td>
                        <td>${cargo.isFragile}</td>
                        <td>${cargo.destination}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showSearchResultFreighters'}">
    <div id="SearchResultFreightersModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>Название перевозчика</th>
                    <th>Налог в %</th>
                    <th>Стоимость за килограмм</th>
                    <th>Стоимость за кубометр</th>
                    <th>Добавочная стоимость за хрупкость</th>

                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="freighter" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${freighter.freighterName}</td>
                        <td>${freighter.tax}</td>
                        <td>${freighter.weightCost}</td>
                        <td>${freighter.sizeCost}</td>
                        <td>${freighter.fragileCost}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showSearchResultShips'}">
    <div id="SearchResultShipsModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>Модель корабля</th>
                    <th>ID команды</th>
                    <th>Размер корабля</th>
                    <th>Вместимость корабля</th>
                    <th>Перевозчик</th>
                    <th>В пути?</th>

                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="ship" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${ship.shipModel.shipModel}</td>
                        <td>${ship.team.teamId}</td>
                        <td>${ship.shipModel.shipSize}</td>
                        <td>${ship.shipModel.shipCapacity}</td>
                        <td>${ship.freighter.freighterName}</td>
                        <td>${ship.inUse}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showSearchResultTeams'}">
    <div id="SearchResultTeamsModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>ID команды</th>
                    <th>Опыт команды</th>
                    <th>Описание</th>


                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="team" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${team.teamId}</td>
                        <td>${team.experience}</td>
                        <td>${team.description}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showSearchResultClients'}">
    <div id="SearchResultClientsModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>ID клиента</th>
                    <th>ФИО</th>
                    <th>Электронная почта</th>
                    <th>Логин</th>
                    <th>Пароль</th>
                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="client" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${client.customerId}</td>
                        <td>${client.fullName}</td>
                        <td>${client.email}</td>
                        <td>${client.login}</td>
                        <td>${client.password}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showSearchResultOrders'}">
    <div id="SearchResultClientsModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>ID клиента</th>
                    <th>ID заказа</th>
                    <th>ФИО клиента</th>
                    <th>Статус заказа</th>
                    <th>Дата заказа</th>
                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="cargo" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${cargo.customer.customerId}</td>
                        <td>${cargo.order.orderId}</td>
                        <td>${cargo.customer.fullName}</td>
                        <td>${cargo.order.status}</td>
                        <td>${cargo.order.date}</td>
                    </tr>
                </c:forEach>


                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showSearchResultWorkers'}">
    <div id="SearchResultWorkersModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>ID сотрудника</th>
                    <th>Паспорт</th>
                    <th>ФИО</th>
                    <th>Дата рождения</th>
                    <th>Должность</th>
                    <th>Дата найма</th>
                    <th>Причал</th>

                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="worker" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${worker.workerId}</td>
                        <td>${worker.passportSerialNumber.passportSerialNumber}</td>
                        <td>${worker.passportSerialNumber.fullName}</td>
                        <td>${worker.passportSerialNumber.birthDate}</td>
                        <td>${worker.position.position}</td>
                        <td>${worker.hiringDate}</td>
                        <td>${worker.dockId.dockId}</td>
                    </tr>
                </c:forEach>


                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>
