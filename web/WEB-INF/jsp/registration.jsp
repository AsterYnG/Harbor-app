<%--
  Created by IntelliJ IDEA.
  User: ssc tuatara
  Date: 20.11.2023
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>>
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
        <h1>Create an Account</h1>
        <form id="registration-form" action="/registration" method="post">
            <div class="form-group">
                <input type="text" id="fullname" name="fullname" placeholder="Full Name" value="${param.fullname}" required>
            </div>

            <div class="form-group">
                <input type="text" id="login" name="login" placeholder="Login" required>
            </div>
            <c:if test="${requestScope.notUnique}">
                <div class="error-message" id="login-error">Логин занят, используйте другой</div>
            </c:if>
            <div class="form-group">
                <input type="email" id="email" name="email" placeholder="Email Address" required>
            </div>

            <div class="form-group">
                <input type="password" id="password" name="password" placeholder="Password" required>
            </div>

<%--            <div class="form-group">--%>
<%--                <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm Password" required>--%>
<%--            </div>--%>


<%--            <div class="checkbox-group">--%>
<%--                <input type="checkbox" id="terms" name="terms" required>--%>
<%--                <label for="terms">I agree to the <a href="#">Terms and Conditions</a></label>--%>
<%--            </div>--%>

            <button type="submit" id="register-btn">Sign Up</button>
        </form>
        <div class="additional-info">
            <p>Already have an account? <a href="#" id="toggle-login">Login</a></p>
        </div>
    </div>
</div>
</body>
</html>