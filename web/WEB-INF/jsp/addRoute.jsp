<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${sessionScope.active == 'addAvailableRoute'}">
    <div id="addAvailableRouteModal" class="modal">


        <form id="addAvailableRouteForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Добавить путь</h2>

            <div class="form-group">
                <label for="routeDirectionCountry">Страна:</label>
                <input placeholder="Америка" value="${param.routeDirection}" type="text" id="routeDirectionCountry" name="routeDirectionCountry" pattern="^[А-Я][а-я]+\D*$"  maxlength="20" required>
            </div>
            <div class="form-group">
            <c:forEach var="error" items="${requestScope.errors}">
                <c:if test="${error.code == 'invalid.Route.countryAndCity'}">
                    <div class="error-message" id="login-error">${error.message}</div>
                </c:if>
            </c:forEach>
                <label for="routeDirectionCity">Город:</label>
                <input placeholder="Лос-Анджелес" value="${param.routeDirection}" type="text" id="routeDirectionCity" name="routeDirectionCity" pattern="^[А-Я][а-я]+\D*$"  maxlength="20" required>
            </div>
            <div class="form-group">
                <label for="duration">Время в пути(ч):</label>
                <input  value="${param.routeDirection}" type="number" id="duration" name="duration"  maxlength="5" required>
            </div>
                <h3>Для каких перевозчиков:</h3>
                <c:forEach var="freighter" items="${sessionScope.freighters}">
                    <div class="form-group">
                    <label for="${freighter.freighterName}">${freighter.freighterName}:</label>
                    <input type="checkbox" id="${freighter.freighterName}" name="${freighter.freighterName}" value="true">
                    </div>
                </c:forEach>



            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>
<script>


    function closeModal() {
        document.getElementById('addAvailableRouteModal').style.display = 'none';
    }




    window.onclick = function(event) {
        if (event.target === document.getElementById('addAvailableRouteModal')) {
            closeModal();
        }

    };
</script>