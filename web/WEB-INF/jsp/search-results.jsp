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
