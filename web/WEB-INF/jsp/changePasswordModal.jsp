<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2>Сменить пароль</h2>
        <form action="${pageContext.request.contextPath}/changePassword" method="post">
            <label for="oldPassword">Старый пароль:</label>
            <input type="password" id="oldPassword" name="oldPassword" required><br>

            <label for="newPassword">Новый пароль:</label>
            <input type="password" id="newPassword" name="newPassword" required><br>

            <input type="submit" value="Изменить пароль">
        </form>
    </div>
</div>

<script>
    function openModal() {
        document.getElementById('myModal').style.display = 'block';
    }

    function closeModal() {
        document.getElementById('myModal').style.display = 'none';
    }

    window.onclick = function(event) {
        if (event.target === document.getElementById('myModal')) {
            closeModal();
        }
    };
</script>