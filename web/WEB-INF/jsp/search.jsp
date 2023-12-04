<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${sessionScope.active == 'availableRoutes'}">
    <div id="availableRoutesSearchFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="availableRoutesSearchForm" class="modal-form" action="${pageContext.request.contextPath}/admin"
              method="post">

                <div class="error-message" id="login-error">${requestScope.error.message}</div>

            <h2>Поиск по базе</h2>

            <div class="form-group">
                <h3>Для каких перевозчиков:</h3>
                <c:forEach var="freighter" items="${sessionScope.freighters}">
                    <div class="form-group">
                        <label for="${freighter.freighterName}">${freighter.freighterName}:</label>
                        <input type="checkbox" id="${freighter.freighterName}" name="${freighter.freighterName}"
                               value="true">
                    </div>
                </c:forEach>
            </div>
            <div class="form-group">
                <h3>Выберите страны:</h3>
                <c:forEach var="route" items="${sessionScope.availableRoutesCountries}">
                    <div class="form-group">
                        <label for="${route}">${route}:</label>
                        <input type="checkbox" id="${route}" name="${route}" value="true">
                    </div>
                </c:forEach>

            </div>

            <div class="form-group">
                <h3>Выберите город:</h3>
                <c:forEach var="route" items="${sessionScope.availableRoutes}">
                    <div class="form-group">
                        <label for="${route.destinationCity}">${route.destinationCity}:</label>
                        <input type="checkbox" id="${route.destinationCity}" name="${route.destinationCity}"
                               value="true">
                    </div>
                </c:forEach>
            </div>
            <h3>Выберите промежуток длительности плавания:</h3>
            <div class="form-group">
                <label for="durationFrom">Длительность от(ч):</label>
                <input type="number" id="durationFrom" name="durationFrom" pattern="^(\S|\d)+$" value="0" max="10000">
            </div>
            <div class="form-group">
                <label for="durationTo">Длительность до(ч):</label>
                <input type="number" id="durationTo" name="durationTo" pattern="^(\S|\d)+$" value="9999" max="10000">
            </div>

            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'cargos'}">
    <div id="cargosFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="cargosForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <div class="error-message" id="login-error">${requestScope.error.message}</div>
            <h2>Поиск по базе</h2>

            <h3>Вес груза от и до (кг):</h3>
            <div class="form-group">
                <label for="weightFrom">Вес от:</label>
                <input type="number" id="weightFrom" name="weightFrom" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="weightTo">Вес до:</label>
                <input type="number" id="weightTo" name="weightTo" pattern="^(\S|\d)+$" max="10000">
            </div>

            <h3>Размер груза от и до (м^3):</h3>
            <div class="form-group">
                <label for="sizeFrom">Размер от:</label>
                <input type="number" id="sizeFrom" name="sizeFrom" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="sizeTo">Размер до:</label>
                <input type="number" id="sizeTo" name="sizeTo" pattern="^(\S|\d)+$" max="10000">
            </div>
            <h3>Id:</h3>
            <div class="form-group">
                <label for="clientId">Id клиента:</label>
                <input type="number" id="clientId" name="clientId" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="orderId">Id Заказа:</label>
                <input type="number" id="orderId" name="orderId" pattern="^(\S|\d)+$" max="10000">
            </div>
            <h3>Страна:</h3>

            <c:forEach var="route" items="${sessionScope.availableRoutesCountries}">
                <div class="form-group">
                    <label for="${route}">${route}:</label>
                    <input type="checkbox" id="${route}" name="${route}" value="true">
                </div>
            </c:forEach>


            <h3>Перевозчик:</h3>
            <c:forEach var="freighter" items="${sessionScope.freighters}">
                <div class="form-group">
                    <label for="${freighter.freighterName}">${freighter.freighterName}:</label>
                    <input type="checkbox" id="${freighter.freighterName}" name="${freighter.freighterName}"
                           value="true">
                </div>
            </c:forEach>

            <h3>Хрупкость груза:</h3>
            <div class="form-group">
                <label for="fragile">Хрупкий или нет:</label>
                <input type="checkbox" id="fragile" name="fragile" value="true">
            </div>

            <div class="form-group">
                <label for="allCargos">Искать все грузы:</label>
                <input type="checkbox" id="allCargos" name="allCargos" value="true">
            </div>


            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'freighters'}">
    <div id="freightersFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="freightersForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <div class="error-message" id="login-error">${requestScope.error.message}</div>
            <h2>Поиск по базе</h2>


            <h3>Название перевозчика:</h3>

            <c:forEach var="freighter" items="${sessionScope.freighters}">
                <div class="form-group">
                    <label for="${freighter.freighterName}">${freighter.freighterName}:</label>
                    <input type="checkbox" id="${freighter.freighterName}" name="${freighter.freighterName}"
                           value="true">
                </div>
            </c:forEach>

            <h3>Налог порту в %:</h3>
            <div class="form-group">
                <label for="taxFrom">Налог от:</label>
                <input type="number" id="taxFrom" name="taxFrom" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="taxTo">Налог до:</label>
                <input type="number" id="taxTo" name="taxTo" pattern="^(\S|\d)+$" max="10000">
            </div>

            <h3>Стоимость за один кг:</h3>
            <div class="form-group">
                <label for="weightCostFrom">Стоимость от:</label>
                <input type="number" id="weightCostFrom" name="weightCostFrom" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="weightCostTo">Стоимость до:</label>
                <input type="number" id="weightCostTo" name="weightCostTo" pattern="^(\S|\d)+$" max="10000">
            </div>

            <h3>Стоимость за один кубометр:</h3>
            <div class="form-group">
                <label for="sizeCostFrom">Стоимость от:</label>
                <input type="number" id="sizeCostFrom" name="sizeCostFrom" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="sizeCostTo">Стоимость до:</label>
                <input type="number" id="sizeCostTo" name="sizeCostTo" pattern="^(\S|\d)+$" max="10000">
            </div>

            <h3>Добавочная стоимость за хрупкость:</h3>
            <div class="form-group">
                <label for="fragileCostFrom">Стоимость от:</label>
                <input type="number" id="fragileCostFrom" name="fragileCostFrom" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="fragileCostTo">Стоимость до:</label>
                <input type="number" id="fragileCostTo" name="fragileCostTo" pattern="^(\S|\d)+$" max="10000">
            </div>
            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'ships'}">
    <div id="shipsFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="shipsForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <div class="error-message" id="login-error">${requestScope.error.message}</div>
            <h2>Поиск по базе</h2>


            <h3>Перевозчик, которому принадлежит корабль:</h3>

            <c:forEach var="freighter" items="${sessionScope.freighters}">
                <div class="form-group">
                    <label for="${freighter.freighterName}">${freighter.freighterName}:</label>
                    <input type="checkbox" id="${freighter.freighterName}" name="${freighter.freighterName}"
                           value="true">
                </div>
            </c:forEach>

            <h3>Модель корабля:</h3>

            <c:forEach var="ship" items="${sessionScope.ships}">
                <div class="form-group">
                    <label for="${ship.shipModel.shipModel}">${ship.shipModel.shipModel}:</label>
                    <input type="checkbox" id="${ship.shipModel.shipModel}" name="${ship.shipModel.shipModel}"
                           value="true">
                </div>
            </c:forEach>

            <h3>Id:</h3>
            <div class="form-group">
                <label for="teamId">Id команды:</label>
                <input type="number" id="teamId" name="teamId" pattern="^(\S|\d)+$" max="10000">
            </div>


            <h3>Размер корабля:</h3>
            <div class="form-group">
                <label for="shipSizeFrom">Размер от:</label>
                <input type="number" id="shipSizeFrom" name="shipSizeFrom" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="shipSizeTo">Размер до:</label>
                <input type="number" id="shipSizeTo" name="shipSizeTo" pattern="^(\S|\d)+$" max="10000">
            </div>

            <h3>Вместительность корабля:</h3>
            <div class="form-group">
                <label for="shipCapacityFrom">Стоимость от:</label>
                <input type="number" id="shipCapacityFrom" name="shipCapacityFrom" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="shipCapacityTo">Стоимость до:</label>
                <input type="number" id="shipCapacityTo" name="shipCapacityTo" pattern="^(\S|\d)+$" max="10000">
            </div>

            <h3>В использовании:</h3>
            <div class="form-group">
                <label for="inUse">В пути или нет:</label>
                <input type="checkbox" id="inUse" name="inUse" value="true">
            </div>
            <div class="form-group">
                <label for="allShips">Искать все корабли:</label>
                <input type="checkbox" id="allShips" name="allShips" value="true">
            </div>


            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>


<c:if test="${sessionScope.active == 'teams'}">
    <div id="teamsFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="teamsForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <div class="error-message" id="login-error">${requestScope.error.message}</div>
            <h2>Поиск по базе</h2>

            <h3>ФИО:</h3>
            <div class="form-group">
                <label for="memberFullName">ФИО участника команды:</label>
                <input type="text" id="memberFullName" name="memberFullName" maxlength="39"
                       pattern="^[А-Я][а-я]* [А-Я][а-я]*( [А-Я][а-я]*)?$">
            </div>

            <h3>Гражданство участника команды:</h3>
            <c:forEach var="teamMember" items="${sessionScope.teamMembers}">
                <div class="form-group">
                    <label for="${teamMember}">${teamMember}:</label>
                    <input type="checkbox" id="${teamMember}" name="${teamMember}"
                           value="true">
                </div>
            </c:forEach>

            <h3>Опыт команды:</h3>
            <div class="form-group">
                <label for="experienceFrom">Опыт от:</label>
                <input type="number" id="experienceFrom" name="experienceFrom" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="experienceTo">Опыт до:</label>
                <input type="number" id="experienceTo" name="experienceTo" pattern="^(\S|\d)+$" max="10000">
            </div>


            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'clients'}">
    <div id="clientsFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="clientsForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <div class="error-message" id="login-error">${requestScope.error.message}</div>
            <h2>Поиск по базе</h2>

            <h3>ФИО:</h3>
            <div class="form-group">
                <label for="customerFullName">ФИО клиента:</label>
                <input type="text" id="customerFullName" name="customerFullName" maxlength="39"
                       pattern="^[А-Я][а-я]* [А-Я][а-я]*( [А-Я][а-я]*)?$">
            </div>

            <h3>Электронная почта:</h3>
            <div class="form-group">
                <label for="customerEmail">Почта клиента:</label>
                <input type="text" id="customerEmail" name="customerEmail" maxlength="39" pattern="^\S+@\S+\.\S+$">
            </div>

            <h3>Логин:</h3>
            <div class="form-group">
                <label for="customerLogin">Логин клиента:</label>
                <input type="text" id="customerLogin" name="customerLogin" maxlength="39" pattern="^\S+$">
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'orders'}">
    <div id="ordersFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>

        <form id="ordersForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <div class="error-message" id="login-error">${requestScope.error.message}</div>
            <h2>Поиск по базе</h2>

            <h3>Id:</h3>
            <div class="form-group">
                <label for="orderClientId">Id клиента:</label>
                <input type="number" id="orderClientId" name="orderClientId" pattern="^(\S|\d)+$" max="10000">
            </div>
            <div class="form-group">
                <label for="orderId">Id заказа:</label>
                <input type="number" id="orderId" name="orderId" pattern="^(\S|\d)+$" max="10000">
            </div>

            <h3>ФИО:</h3>
            <div class="form-group">
                <label for="orderCustomerFullName">ФИО клиента:</label>
                <input type="text" id="orderCustomerFullName" name="orderCustomerFullName" maxlength="39"
                       pattern="^[А-Я][а-я]* [А-Я][а-я]*( [А-Я][а-я]*)?$">
            </div>
            <h3>Дата заказа:</h3>
            <div class="form-group">
                <label for="orderDateFrom">Дата от:</label>
                <input type="datetime-local" id="orderDateFrom" name="orderDateFrom" min="2000-01-01" max="2023-12-02">
            </div>
            <div class="form-group">
                <label for="orderDateTo">Дата от:</label>
                <input type="datetime-local" id="orderDateTo" name="orderDateTo" min="2000-01-01" max="2023-12-02">
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

<c:if test="${sessionScope.active == 'workers'}">
    <div id="workersFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="workersForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <div class="error-message">${requestScope.error.message}</div>
            <h2>Поиск по базе</h2>

            <h3>Причал:</h3>
            <div class="form-group">
                <label for="dockId">Номер причала:</label>
                <input type="number" id="dockId" name="dockId" pattern="^(\S|\d)+$" max="100">
            </div>
            <h3>Серия и номер паспорта:</h3>
            <div class="form-group">
                <label for="passportSerialNumber">Серия и номер 10 цифр:</label>
                <input type="text" id="passportSerialNumber" name="passportSerialNumber" pattern="^(\S|\d)+$"
                       maxlength="10">
            </div>

            <h3>Возраст:</h3>
            <div class="form-group">
                <label for="ageFrom">От:</label>
                <input type="number" id="ageFrom" name="ageFrom" pattern="^(\S|\d)+$" maxlength="2">
            </div>
            <div class="form-group">
                <label for="ageTo">До:</label>
                <input type="number" id="ageTo" name="ageTo" pattern="^(\S|\d)+$" maxlength="2">
            </div>

            <h3>Зарплата:</h3>
            <div class="form-group">
                <label for="salaryFrom">От:</label>
                <input type="number" id="salaryFrom" name="salaryFrom" pattern="^(\S|\d)+$" maxlength="8">
            </div>
            <div class="form-group">
                <label for="salaryTo">До:</label>
                <input type="number" id="salaryTo" name="salaryTo" pattern="^(\S|\d)+$" maxlength="8">
            </div>

            <h3>Должность:</h3>
            <c:forEach var="position" items="${sessionScope.positionList}">
                <div class="form-group">
                    <label for="${position}">${position}:</label>
                    <input type="checkbox" id="${position}" name="${position}"
                           value="true">
                </div>
            </c:forEach>
            <h3>Дата найма:</h3>
            <div class="form-group">
                <label for="hiringDateFrom">Дата от:</label>
                <input type="date" id="hiringDateFrom" name="hiringDateFrom" min="2000-01-01" max="2023-12-02">
            </div>
            <div class="form-group">
                <label for="hiringDateTo">Дата от:</label>
                <input type="date" id="hiringDateTo" name="hiringDateTo" min="2000-01-01" max="2023-12-02">
            </div>

            <h3>ФИО:</h3>
            <div class="form-group">
                <label for="workerFullName">ФИО работника:</label>
                <input type="text" id="workerFullName" name="workerFullName" maxlength="39"
                       pattern="^[А-Я][а-я]* [А-Я][а-я]*( [А-Я][а-я]*)?$">
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>

<%--Сортировка--%>
<c:if test="${sessionScope.active == 'sortAvailableRoutes'}">
    <div id="sortRoutesFormModal" class="modal">

        <form id="sortRoutesForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Сортировка</h2>

            <div class="form-group">
                <label for="sortBy">Сортировать по:</label>
                <select id="sortBy" name="sortBy">
                    <option value="country">Страна</option>
                    <option value="city">Город</option>
                    <option value="duration">Длительность</option>
                    <option value="freighter">Перевозчик</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'sortCargos'}">
    <div id="sortRoutesFormModal" class="modal">

        <form id="sortCargosForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Сортировка</h2>

            <div class="form-group">
                <label for="sortBy1">Сортировать по:</label>
                <select id="sortBy1" name="sortBy">
                    <option value="size">Размер</option>
                    <option value="weight">Вес</option>
                    <option value="country">Страна</option>
                    <option value="fullName">ФИО</option>
                    <option value="freighter">Перевозчик</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'sortFreighters'}">
    <div id="sortFreightersFormModal" class="modal">

        <form id="sortFreightersForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Сортировка</h2>

            <div class="form-group">
                <label for="sortBy2">Сортировать по:</label>
                <select id="sortBy2" name="sortBy">
                    <option value="weightCost">Стоимость за кг</option>
                    <option value="sizeCost">Стоимость за м^3</option>
                    <option value="fragileCost">Стоимость за хрупкость</option>
                    <option value="tax">Налог</option>
                    <option value="freighter">Перевозчик</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'sortShips'}">
    <div id="sortShipsFormModal" class="modal">

        <form id="sortShipsForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Сортировка</h2>

            <div class="form-group">
                <label for="sortBy3">Сортировать по:</label>
                <select id="sortBy3" name="sortBy">
                    <option value="teamId">ID команды</option>
                    <option value="shipSize">Размер корабля</option>
                    <option value="shipCapacity">Вместимость корабля</option>
                    <option value="freighter">Перевозчик</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'sortTeams'}">
    <div id="sortTeamsFormModal" class="modal">

        <form id="sortTeamsForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Сортировка</h2>

            <div class="form-group">
                <label for="sortBy4">Сортировать по:</label>
                <select id="sortBy4" name="sortBy">
                    <option value="experience">Опыт</option>
                    <option value="teamId">ID команды</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'sortClients'}">
    <div id="sortClientsFormModal" class="modal">

        <form id="sortClientsForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Сортировка</h2>

            <div class="form-group">
                <label for="sortBy5">Сортировать по:</label>
                <select id="sortBy5" name="sortBy">
                    <option value="clientId">ID клиента</option>
                    <option value="fullName">ФИО</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'sortOrders'}">
    <div id="sortOrdersFormModal" class="modal">

        <form id="sortOrdersForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Сортировка</h2>

            <div class="form-group">
                <label for="sortBy6">Сортировать по:</label>
                <select id="sortBy6" name="sortBy">
                    <option value="clientId">ID клиента</option>
                    <option value="orderId">ID заказа</option>
                    <option value="fullName">ФИО</option>
                    <option value="date">Дата</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'sortWorkers'}">
    <div id="sortWorkersFormModal" class="modal">

        <form id="sortWorkersForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Сортировка</h2>

            <div class="form-group">
                <label for="sortBy7">Сортировать по:</label>
                <select id="sortBy7" name="sortBy">
                    <option value="workerId">ID работника</option>
                    <option value="fullName">ФИО</option>
                    <option value="birthDate">Дата рождения</option>
                    <option value="hiringDate">Дата найма</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'sortLog'}">
    <div id="sortLogFormModal" class="modal">

        <form id="sortLogForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Сортировка</h2>

            <div class="form-group">
                <label for="sortBy8">Сортировать по:</label>
                <select id="sortBy8" name="sortBy">
                    <option value="logId">Номер записи</option>
                    <option value="freighter">Перевозчик</option>
                    <option value="date">Дата</option>
                    <option value="shipId">Номер корабля</option>
                    <option value="shipCapacity">Вместимость корабля</option>
                    <option value="shipSize">Размер корабля</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>


