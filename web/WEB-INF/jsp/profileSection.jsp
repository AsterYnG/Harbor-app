<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="profile-section">
    <h2>Редактирование профиля клиента</h2>
    <form action="${pageContext.request.contextPath}/updateProfile" method="post">
        <!-- Форма для изменения ФИО и логина -->
        <label for="fullName">ФИО:</label>
        <input type="text" id="fullName" name="fullName" required><br>

        <label for="login">Логин:</label>
        <input type="text" id="login" name="login" required><br>

        <button type="button" onclick="openModal()">Сменить пароль</button>
        <input type="submit" value="Сохранить">
    </form>
</section>