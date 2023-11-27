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
