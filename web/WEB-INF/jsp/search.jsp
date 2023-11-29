<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${sessionScope.active == 'availableRoutes'}">
    <div id="availableRoutesSearchFormModal" class="modal">
        <form id="availableRoutesSearchForm" class="modal-form" action="${pageContext.request.contextPath}/admin"
              method="post">
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
        <form id="cargosForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Поиск по базе</h2>

            <h3>Вес груза от и до (кг):</h3>
            <div class="form-group">
                <label for="weightFrom">Вес от:</label>
                <input type="number" id="weightFrom" name="weightFrom" pattern="^(\S|\d)+$"  max="10000">
            </div>
            <div class="form-group">
                <label for="weightTo">Вес до:</label>
                <input type="number" id="weightTo" name="weightTo" pattern="^(\S|\d)+$"  max="10000">
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
                <input type="number" id="clientId" name="clientId" pattern="^(\S|\d)+$"  max="10000">
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



            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'freighters'}">
    <div id="freightersFormModal" class="modal">
        <form id="freightersForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
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
        <form id="shipsForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
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
                <input type="number" id="teamId" name="teamId" pattern="^(\S|\d)+$"  max="10000">
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
        <form id="teamsForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Поиск по базе</h2>

            <h3>ФИО:</h3>
            <div class="form-group">
                <label for="memberFullName">ФИО участника команды:</label>
                <input type="text" id="memberFullName" name="memberFullName" maxlength="39" pattern="^[А-Я][а-я]* [А-Я][а-я]*( [А-Я][а-я]*)?$"  >
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
        <form id="clientsForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Поиск по базе</h2>

            <h3>ФИО:</h3>
            <div class="form-group">
                <label for="customerFullName">ФИО клиента:</label>
                <input type="text" id="customerFullName" name="customerFullName" maxlength="39" pattern="^[А-Я][а-я]* [А-Я][а-я]*( [А-Я][а-я]*)?$"  >
            </div>

            <h3>Электронная почта:</h3>
            <div class="form-group">
                <label for="customerEmail">Почта клиента:</label>
                <input type="text" id="customerEmail" name="customerEmail" maxlength="39" pattern="^\S+@\S+\.\S+$"  >
            </div>

            <h3>Логин:</h3>
            <div class="form-group">
                <label for="customerLogin">Логин клиента:</label>
                <input type="text" id="customerLogin" name="customerLogin" maxlength="39" pattern="^\S+$"  >
            </div>

            <div class="form-group">
                <button type="submit">Искать</button>
            </div>
        </form>
    </div>
</c:if>