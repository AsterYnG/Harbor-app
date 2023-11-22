<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Админская панель</title>
    <!-- Подключение стилей, скриптов и других ресурсов -->
    <style>
        <%@include file="css/admin.css"%>
    </style>
</head>
<body>
<!-- Шапка страницы -->
<header>
    <!-- Данные админа: ФИО, логин -->
    <div class="admin-info">
        <p>ФИО админа: ${sessionScope.loggedIn.fullName}</p>
        <p>Логин: ${sessionScope.loggedIn.password}</p>
    </div>
</header>

<!-- Основная часть страницы -->
<main>
    <!-- Кнопка внесения работника -->
    <form action="/button" method="get">
        <button onclick="showEmployeeForm()" name="buttonAddEmployee" value="true" >Добавить работника</button>

        <!-- Кнопка вывода всех работников -->
        <button onclick="showAllEmployees()" name="buttonShowAllEmployees" value="true">Все работники</button>

        <!-- Кнопка внесения перевозчика -->
        <button onclick="showCarrierForm()" name="buttonAddFreighter" value="true">Добавить перевозчика</button>

        <!-- Кнопка вывода всех перевозчиков -->
        <button onclick="showAllFreighters()" name="buttonShowAllFreighters" value="true">Все перевозчики</button>

        <!-- Кнопка вывода всех клиентов -->
        <button onclick="showAllClients()" name="buttonShowAllClients" value="true">Все клиенты</button>

        <!-- Кнопка вывода всех плаваний кораблей -->
        <button onclick="showAllShipVoyages()" name="buttonShowVoyageLog" value="true">Плавания кораблей</button>

        <!-- Кнопка вывода всех команд для кораблей -->
        <button onclick="showAllShipCrews()" name="buttonShowAllTeams" value="true">Команды для кораблей</button>
    </form>
</main>

<!-- Модальные окна для форм -->
<div id="employeeFormModal" class="modal">
    <!-- Форма для внесения работника -->
    <!-- Здесь нужно описать HTML-структуру формы -->
</div>

<div id="carrierFormModal" class="modal">
    <!-- Форма для внесения перевозчика -->
    <!-- Здесь нужно описать HTML-структуру формы -->
</div>

<jsp:include page="popup-table.jsp" />
</body>
</html>

