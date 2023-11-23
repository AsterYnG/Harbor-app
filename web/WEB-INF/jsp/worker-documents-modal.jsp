<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${sessionScope.active == 'addEmployeePassport'}">
    <div id="employeePassportFormModal" class="modal">
        <form id="employeePassportForm" class="modal-form" action="/admin" method="post">
            <h2>Добавить Паспорт</h2>
            <div class="form-group">
                <label for="serialNumber">Серия и номер паспорта:</label>
                <input type="text" id="serialNumber" name="serialNumber" pattern="^\d{10}$" maxlength="10" required>
            </div>
            <div class="form-group">
                <label for="birthDate">Дата рождения:</label>
                <input type="date" id="birthDate" name="birthDate" required>
            </div>

            <div class="form-group">
                <label for="sex">Пол:</label>
                <select id="sex" name="sex">
                    <option value="Мужской">Мужской</option>
                    <option value="Женский">Женский</option>
                </select>
            </div>

            <div class="form-group">
                <label for="citizenship">Гражданство:</label>
                <select id="citizenship" name="citizenship">
                    <option value="Россия">Россия</option>
                </select>
            </div>
            <div class="form-group">
                <label for="region">Регион:</label>
                <input type="text" id="region" name="region"  required>
            </div>
            <div class="form-group">
                <label for="city">Город:</label>
                <input type="text" id="city" name="city"  required>
            </div>
            <div class="form-group">
                <label for="street">Улица:</label>
                <input type="text" id="street" name="street"  required>
            </div>
            <div class="form-group">
                <label for="house">Дом:</label>
                <input type="text" id="house" name="house"  required>
            </div>
            <div class="form-group">
                <label for="flat">Квартира(0, если живете в частном доме:</label>
                <input type="text" id="flat" name="flat" pattern="^\d+$" required>
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
        <form id="employeeMedicalCardForm" class="modal-form" action="/admin" method="post">
            <h2>Добавить Медицинскую книжку</h2>
            <div class="form-group">
                <label for="medSerialNumber">Серия и номер Мед. Книжки:</label>
                <input type="text" id="medSerialNumber" name="medSerialNumber" pattern="^(\d|[А-Я]){11}$" maxlength="11" required>
            </div>
            <div class="form-group">
                <label for="illness">Хроническое заболевание:</label>
                <input type="text" id="illness" name="illness" required>
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
        <form id="employeeEmploymentCardForm" class="modal-form" action="/admin" method="post">
            <h2>Добавить книжку трудоустройства</h2>
            <div class="form-group">
                <label for="employmentSerialNumber">Серия и номер книжки трудоустройства:</label>
                <input type="text" id="employmentSerialNumber" name="employmentSerialNumber" required>
            </div>
            <div class="form-group">
                <label for="previousJob">Предыдущая работа:</label>
                <input type="text" id="previousJob" name="previousJob" required>
            </div>

            <div class="form-group">
                <label for="experience">Стаж на предыдущей работе:</label>
                <input type="text" id="experience" name="experience" required>
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
        <form id="employeeEducationCardForm" class="modal-form" action="/admin" method="post">
            <h2>Добавить документ об образовании</h2>
            <div class="form-group">
                <label for="educationSerialNumber">Серия и номер документа:</label>
                <input type="text" id="educationSerialNumber" name="educationSerialNumber" pattern="^(\d|[А-Я]){11}$" maxlength="11" required>
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