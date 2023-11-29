<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Страница клиента морского порта</title>
    <style>
        <%@include file="css/admin.css"%>
    </style>
</head>
<header>
    <form action="/logout" method="post">
        <button class="logout-btn">Выход</button>
    </form>
</header>

<body>
<jsp:include page="profileSection.jsp"/>

<jsp:include page="changePasswordModal.jsp"/>

<jsp:include page="orderSection.jsp" />

<jsp:include page="create-order-modal.jsp"/>
</body>
</html>