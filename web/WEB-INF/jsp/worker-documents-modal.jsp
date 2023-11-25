<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${sessionScope.active == 'addEmployeePassport'}">
    <div id="employeePassportFormModal" class="modal">

        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="employeePassportForm" class="modal-form" action="/admin" method="post">

            <h2>Добавить Паспорт</h2>
            <c:forEach var="error" items="${requestScope.errors}">
            <c:if test="${error.code == 'invalid.Passport.SerialNumber'}">
                <div class="error-message" id="login-error">${error.message}</div>
            </c:if>
            </c:forEach>
            <div class="form-group">
                <label for="serialNumber">Серия и номер паспорта:</label>
                <input placeholder="xxxxxxxxxx" value="${param.serialNumber}" type="text" id="serialNumber" name="serialNumber" pattern="^\d{10}$" maxlength="10" required>
            </div>

            <c:forEach var="error" items="${requestScope.errors}">
                <c:if test="${error.code == 'invalid.Passport.birthDate'}">
                    <div class="error-message" id="login-error">${error.message}</div>
                </c:if>
            </c:forEach>
            <div class="form-group">
                <label for="birthDate">Дата рождения:</label>
                <input value="${param.birthDate}" type="date" id="birthDate" name="birthDate"  required>
            </div>
            <c:forEach var="error" items="${requestScope.errors}">
                <c:if test="${error.code == 'invalid.Passport.sex'}">
                    <div class="error-message" id="login-error">${error.message}</div>
                </c:if>
            </c:forEach>
            <div class="form-group">
                <label for="sex">Пол:</label>
                <select id="sex" name="sex">
                    <option value="Мужской">Мужской</option>
                    <option value="Женский">Женский</option>
                </select>
            </div>
            <c:forEach var="error" items="${requestScope.errors}">
                <c:if test="${error.code == 'invalid.Passport.citizenship'}">
                    <div class="error-message" id="login-error">${error.message}</div>
                </c:if>
            </c:forEach>
            <div class="form-group">
                <label for="citizenship">Гражданство:</label>
                <select id="citizenship" name="citizenship">
                    <option value="Россия">Россия</option>
                </select>
            </div>
            <div class="form-group">
                <label for="region">Регион:</label>
                <input placeholder="Московская область" value="${param.region}" type="text" id="region" name="region" maxlength="30" pattern="^\D+$"  required>
            </div>
            <div class="form-group">
                <label for="city">Город:</label>
                <input placeholder="Москва" value="${param.city}" type="text" id="city" name="city" maxlength="30" pattern="^\D+$"  required>
            </div>
            <div class="form-group">
                <label for="street">Улица:</label>
                <input placeholder="Лобачевского" value="${param.street}" type="text" id="street" name="street" maxlength="30" pattern="^\D+$"  required>
            </div>
            <div class="form-group">
                <label for="house">Дом:</label>
                <input placeholder="88к5" value="${param.house}" type="text" id="house" name="house" maxlength="30" pattern="^\S+$"  required>
            </div>
            <div class="form-group">
                <label for="flat">Квартира(0, если живете в частном доме):</label>
                <input value="${param.flat}" type="text" id="flat" name="flat" pattern="^\d+$" maxlength="3" required>
            </div>

            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'addEmployeeMedicalCard'}">
    <div id="employeeMedicalCardFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>

        <form id="employeeMedicalCardForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Добавить Медицинскую книжку</h2>

            <c:if test="${requestScope.medicalCardIsNotUnique}">
                <div  class="error-message" id="login-error">Медицинская книжка такой серией и номером уже существует!</div>
            </c:if>
            <div class="form-group">
                <label for="medSerialNumber">Серия и номер Мед. Книжки:</label>
                <input placeholder="50АПxxxxxxx"  value="${param.medSerialNumber}" type="text" id="medSerialNumber" name="medSerialNumber" pattern="^(\d|[А-Я]){11}$" maxlength="11" required>
            </div>
            <div class="form-group">
                <label for="illness">Хроническое заболевание:</label>
                <input placeholder="Простатит" value="${param.illness}" type="text" id="illness" name="illness" maxlength="30" pattern="^\S+$" required>
            </div>

            <div class="form-group">
                <label for="hivStatus">Вич статус:</label>
                <select id="hivStatus" name="hivStatus">
                    <option value="true">Да</option>
                    <option value="false">Нет</option>
                </select>
            </div>


            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'addEmployeeEmploymentCard'}">
    <div id="employeeEmploymentCardFormModal" class="modal">
        <form action="/${pageContext.request.contextPath}admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="employeeEmploymentCardForm" class="modal-form" action="/admin" method="post">
            <h2>Добавить книжку трудоустройства</h2>
            <c:if test="${requestScope.employmentCardIsNotUnique}">
                <div class="error-message" id="login-error">Трудовая книжка такой серией и номером уже существует!</div>
            </c:if>
            <div class="form-group">
                <label for="employmentSerialNumber">Серия и номер книжки трудоустройства:</label>
                <input placeholder="50АПxxxxxxx" value="${param.employmentSerialNumber}" type="text" id="employmentSerialNumber" name="employmentSerialNumber" pattern="^(\d|[А-Я]){11}$" maxlength="11" required>
            </div>
            <div class="form-group">
                <label for="previousJob">Предыдущая работа:</label>
                <input placeholder="Грузчик" value="${param.previousJob}" type="text" id="previousJob" name="previousJob" maxlength="30" pattern="^\D+$"  required>
            </div>

            <div class="form-group">
                <label for="experience">Стаж на предыдущей работе:</label>
                <input value="${param.experience}" type="text" id="experience" name="experience"  maxlength="1" pattern="^\d+$" required>
            </div>
            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'addEmployeeEducationCard'}">
    <div id="employeeEducationCardFormModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <button value="true" name="exit" class="close-button">&#10006;</button>
        </form>
        <form id="employeeEducationCardForm" class="modal-form" action="${pageContext.request.contextPath}/admin" method="post">
            <h2>Добавить документ об образовании</h2>
            <c:if test="${requestScope.educationCardIsNotUnique}">
                <div class="error-message" id="login-error">Документ с такой серией и номером уже существует!</div>
            </c:if>
            <div class="form-group">
                <label for="educationSerialNumber">Серия и номер документа:</label>
                <input placeholder="50АПxxxxxxx" value="${param.educationSerialNumber}" type="text" id="educationSerialNumber" name="educationSerialNumber" pattern="^(\d|[А-Я]){11}$" maxlength="11" required>
            </div>
            <div class="form-group">
                <label for="grade">Уровень образования:</label>
                <select id="grade" name="grade">
                    <option value="Среднее общее">Среднее общее</option>
                    <option value="Высшее образование">Высшее образование</option>
                    <option value="Среднее профессиональное">Среднее профессиональное</option>
                    <option value="Основное общее">Основное общее</option>
                </select>
            </div>

            <div class="form-group">
                <label for="establishment">Образовательная организация:</label>
                <select id="establishment" name="establishment">
                    <option value="МБОУ Лицей №24">МБОУ Лицей №24</option>
                    <option value="РТУ МИРЭА">РТУ МИРЭА</option>
                    <option value="ВИТИ НИЯУ МИФИ">ВИТИ НИЯУ МИФИ</option>
                    <option value="ВТИДБиД">ВТИДБиД</option>
                </select>
            </div>
            <!-- Другие поля для ввода данных работника -->

            <div class="form-group">
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
</c:if>