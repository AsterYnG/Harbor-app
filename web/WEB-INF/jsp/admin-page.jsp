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
    <form action="/logout" method="post">
    <button class="logout-btn">Выход</button>
    </form>
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
        <button onclick="showAllShipCrews()" name="buttonAddAvailableRoute" value="true">Добавить путь</button>
        <button class="search-btn" onclick="showAllShipCrews()" name="buttonSearch" value="true">Поиск</button>
        <button class="search-btn" onclick="showAllShipCrews()" name="buttonSort" value="true">Сортировать по</button>
    </form>
w
</main>

<!-- Модальные окна для форм -->


<jsp:include page="popup-table.jsp" />
<jsp:include page="worker-documents-modal.jsp"/>
<jsp:include page="addRoute.jsp"/>
<jsp:include page="search.jsp"/>
<jsp:include page="search-results.jsp"/>
</body>
</html>

