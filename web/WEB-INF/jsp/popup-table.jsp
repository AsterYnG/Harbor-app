<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${sessionScope.active == 'showWorkers'}">
    <div id="allEmployeesModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>ID работника</th>
                    <th>Паспорт</th>
                    <th>ФИО</th>
                    <th>Дата рождения</th>
                    <th>Должность</th>
                    <th>Дата найма</th>
                    <th>Документ об трудоустройстве</th>
                    <th>Документ об образовании</th>
                    <th>Мед книжка</th>
                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="customer" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${customer.workerId}</td>
                        <td>${customer.passportSerialNumber.passportSerialNumber}</td>
                        <td>${customer.passportSerialNumber.fullName}</td>
                        <td>${customer.passportSerialNumber.birthDate}</td>
                        <td>${customer.position.position}</td>
                        <td>${customer.hiringDate}</td>
                        <td>${customer.employmentSerialNumber.employmentSerialNumber}</td>
                        <td>${customer.educationSerialNumber.educationSerialNumber}</td>
                        <td>${customer.medSerialNumber.medSerialNumber}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showFreighters'}">
    <div id="allFreightersModal" class="modal-table1">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Налог</th>
                    <th>Цена за 1кг</th>
                    <th>Цена за кубометр</th>
                    <th>Цена за хрупкость</th>
                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="customer" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${customer.freighterName}</td>
                        <td>${customer.tax}</td>
                        <td>${customer.weightCost}</td>
                        <td>${customer.sizeCost}</td>
                        <td>${customer.fragileCost}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>


<c:if test="${sessionScope.active == 'showCustomers'}">
    <div id="allFreightersModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>ФИО</th>
                    <th>Логин</th>
                    <th>Почта</th>
                    <th>Пароль</th>
                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="customer" items="${sessionScope.searchResult}">
                    <tr>
                        <td>${customer.password}</td>
                        <td>${customer.fullName}</td>
                        <td>${customer.login}</td>
                        <td>${customer.email}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'addEmployee'}">
    <div id="employeeFormModal" class="modal">
        <form id="employeeForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Добавить работника</h2>
            <div class="form-group">
                <label for="employeeFullName">ФИО:</label>
                <input type="text" id="employeeFullName" name="employeeFullName" maxlength="39" pattern="^[А-Я][а-я]* [А-Я][а-я]*( [А-Я][а-я]*)?$"  required>
            </div>
            <div class="form-group">
                <label for="employeePosition">Должность:</label>
                <select id="employeePosition" name="employeePosition">
                    <c:forEach var="position" items="${sessionScope.positions}">
                        <option value="${position.position}">${position.position}</option>
                    </c:forEach>
                </select>
            </div>
            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'addFreighter'}">
    <div id="freighterFormModal" class="modal">
        <form id="freighterForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Добавить Перевозчика</h2>
            <div class="form-group">
                <label for="freighterName">Название перевозчика:</label>
                <input type="text" id="freighterName" name="freighterName" maxlength="30" placeholder="Балтимор" pattern="^([а-яА-я]+ ?)+$" required>
            </div>
            <div class="form-group">
                <label for="weightCost">Стоимость за 1 кг груза:</label>
                <input type="number" id="weightCost" name="weightCost" maxlength="4" pattern="^/d+$" max="1000000" required>
            </div>
            <div class="form-group">
                <label for="sizeCost">Стоимость за 1 кубометр груза:</label>
                <input type="number" id="sizeCost" name="sizeCost" maxlength="4" pattern="^/d+$" max="1000000" required>
            </div>
            <div class="form-group">
                <label for="tax">Тариф за пользование услугами порта в % :</label>
                <input type="number" id="tax" name="tax" maxlength="2" pattern="^/d+$" max="100" required>
            </div>
            <div class="form-group">
                <label for="fragileCost">Стоимость за хрупкость груза:</label>
                <input type="number" id="fragileCost" name="fragileCost" maxlength="4" max="1000000" pattern="^/d+$" required>
            </div>
            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>
<c:if test="${sessionScope.active == 'showVoyageLog'}">
    <div id="allFreightersModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>Номер плавания</th>
                    <th>Перевозчик</th>
                    <th>Дата</th>
                    <th>Время</th>
                    <th>Номер корабля</th>
                    <th>Модель корабля</th>
                    <th>Вместимость корабля</th>
                    <th>Размер корабля</th>
                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="voyage" items="${sessionScope.voyageLog}">
                    <tr>
                        <td>${voyage.logId}</td>
                        <td>${voyage.ship.freighter.freighterName}</td>
                        <td>${voyage.shipmentDate.year}.${voyage.shipmentDate.monthValue}.${voyage.shipmentDate.dayOfMonth}</td>
                        <td>${voyage.shipmentDate.hour}:${voyage.shipmentDate.minute}:${voyage.shipmentDate.second}</td>
                        <td>${voyage.ship.shipId}</td>
                        <td>${voyage.ship.shipModel.shipModel}</td>
                        <td>${voyage.ship.shipModel.shipCapacity}</td>
                        <td>${voyage.ship.shipModel.shipSize}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showSearch'}">
    <div id="searchFormModal" class="modal">
        <form id="searchForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Поиск по базе</h2>

            <div class="form-group">
                <label for="selectedTable">Выберите по какому разделу искать:</label>
                <select id="selectedTable" name="selectedTable">

                        <option value="availableRoutes">Доступные пути</option>
                        <option value="cargos">Грузы</option>
                        <option value="freighters">Перевозчики</option>
                        <option value="ships">Корабли</option>
                        <option value="teams">Команды на кораблях</option>
                        <option value="clients">Клиенты</option>
                        <option value="orders">Заказы</option>
                        <option value="workers">Работники и их данные</option>

                </select>
            </div>
            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'deleteWorker'}">
    <div id="deleteWorkerFormModal" class="modal">
        <form id="deleteWorkerForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Удалить работника</h2>
            <c:forEach var="error" items="${requestScope.errors}">
                <c:if test="${error.code == 'invalid.Passport.SerialNumber'}">
                    <div class="error-message" id="login-error">${error.message}</div>
                </c:if>
            </c:forEach>
            <div class="form-group">
                <label for="passport">Паспорт:</label>
                <input type="text" id="passport" name="passport" maxlength="10" pattern="^\d{10}$" placeholder="6017xxxxxx" required>
            </div>

            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Удалить</button>
            </div>
        </form>
    </div>
</c:if>



<script>


     function closeModal() {
         document.getElementById('employeeFormModal').style.display = 'none';
     }
     function closeModal1() {
         document.getElementById('freighterFormModal').style.display = 'none';
     }

     function closeModal2() {
         document.getElementById('addAvailableRouteModal').style.display = 'none';
     }
     function closeModal3() {
         document.getElementById('searchFormModal').style.display = 'none';
     }



     window.onclick = function(event) {
         if (event.target === document.getElementById('employeeFormModal')) {
             closeModal();
         }
         if (event.target === document.getElementById('freighterFormModal')) {
             closeModal1();
         }
         if (event.target === document.getElementById('addAvailableRouteModal')) {
             closeModal2();
         }
         if (event.target === document.getElementById('searchFormModal')) {
             closeModal3();
         }

     };


</script>
