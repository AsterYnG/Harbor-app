<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${sessionScope.active == 'changePassword'}">
    <div id="changePasswordFormModal" class="modal">
        <div class="modal-content">
            <form id="changePasswordForm" class="modal-form" action="/client" method="post">
                <h2>Изменить пароль</h2>
                <c:forEach var="error" items="${requestScope.errors}">
                    <c:if test="${error.code == 'invalid.OldPassword'}">
                        <div class="error-message" id="login-error">${error.message}</div>
                    </c:if>
                </c:forEach>
                <label for="oldPassword">Введите старый пароль:</label>
                <input type="password" id="oldPassword" name="oldPassword" placeholder="Старый пароль" pattern="^\S+$" required><br>

                <label for="newPassword">Введите новый пароль:</label>
                <input type="password" id="newPassword" name="newPassword" placeholder="Новый пароль" pattern="^\S+$" required><br>

                <div class="form-group">
                    <button type="submit">Готово</button>
                </div>
            </form>
        </div>
    </div>
</c:if>