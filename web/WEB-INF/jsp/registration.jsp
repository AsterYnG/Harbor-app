<%--
  Created by IntelliJ IDEA.
  User: ssc tuatara
  Date: 20.11.2023
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
    <style>
        <%@include file="css/styles1.css"%>
    </style>
    <script type="text/javascript">
        <%@include file="js/script.js"%>
    </script>
</head>
<body>
<div class="container">
    <div class="registration-form">
        <h1>Регистрация</h1>
        <form id="registration-form" action="/registration" method="post">
            <div class="form-group">
                <input type="text" id="fullname" name="fullname" placeholder="ФИО" value="${param.fullname}" maxlength="49"  required>
            </div>

            <c:if test="${requestScope.notUniqueLogin}">
                <div class="error-message-login" id="login-error">Логин занят, используйте другой</div>
            </c:if>

            <div class="form-group">
                <input type="text" id="login" name="login" placeholder="Логин" value="${param.login}" maxlength="30" pattern="^\S+$" required>
            </div>

            <c:if test="${requestScope.notUniqueEmail}">
                <div class="error-message-email" id="email-error">Почта занята, используйте другую</div>
            </c:if>

            <div class="form-group">
                <input type="text" id="email" name="email" placeholder="Электронная почта" value="${param.email}" pattern="^\S+@\S+\.\S+$" required>
            </div>

            <div class="form-group">
                <input type="password" id="password" name="password" placeholder="Пароль" pattern="^\S+$" value="${param.password}"
                       required>
            </div>


            <button type="submit" id="register-btn">Зарегистрироваться</button>
        </form>
        <div class="additional-info">
            <p>Есть аккаунт? <a href="/login" id="toggle-login">Войти</a></p>
        </div>
    </div>


</div>
</body>
</html>