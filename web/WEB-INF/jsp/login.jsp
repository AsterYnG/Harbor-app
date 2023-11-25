<%--
  Created by IntelliJ IDEA.
  User: ssc tuatara
  Date: 21.11.2023
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Login</title>
    <style>
        <%@include file="css/login-style.css"%>
    </style>

</head>
<body>
<div class="container">
    <div class="login-box">
        <h1>Аутентификация</h1>
        <form id="login-form" action="/login" method="post">
            <div class="input-group">
                <input type="text" id="login" name="login" placeholder="Логин" value="${requestScope.login}" maxlength="15" pattern="^\S+$" required>
            </div>

            <div class="input-group">
                <input type="password" id="password" name="password" placeholder="Пароль" value="${requestScope.password}" maxlength="15" pattern="^\S+$" required>
            </div>

            <button type="submit" id="login-btn">Войти</button>
        </form>

        <div class="additional-info">
            <p>Нет аккаунта? <a href="/registration">Регистрация</a></p>
        </div>
    </div>
</div>
<c:if test="${requestScope.invalidData}">
<div class="error-popup" id="error-popup">
    <p>Invalid username or password.</p>
</div>
</c:if>

<c:if test="${sessionScope.success}">
    <div class="popup" id="success-popup">
        <p>Registration Successful!</p>
    </div>
</c:if>
<script type="text/javascript"><%@include file="js/login-script.js"%></script>
</body>
</html>
