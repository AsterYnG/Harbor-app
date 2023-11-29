<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="profile-section">
    <h2>Ваш профиль</h2>
    <div class="client-info">
        <p>Ваше ФИО: ${sessionScope.loggedIn.fullName}</p>
        <p>Ваш логин: ${sessionScope.loggedIn.login}</p>
        <p>Ваш email: ${sessionScope.loggedIn.email}</p>
    </div>
    <form action="/buttonClient" method="get">
        <button onclick="changeClientPassword()" name="buttonChangePassword" value="true" >Изменить пароль</button>
    </form>
</section>