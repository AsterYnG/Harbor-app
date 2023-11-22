<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${sessionScope.active == 'showWorkers'}">
    <div id="allEmployeesModal" class="modal-table">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <c:forEach var="name" items="${sessionScope.columnNames}">
                        <th>${name}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="freighter" items="${sessionScope.workers}">
                    <tr>
                        <td>${freighter.hiringDate}</td>
                        <td>${freighter.employmentSerialNumber}</td>
                        <td>${freighter.fullName}</td>
                        <td>${freighter.position}</td>
                        <td>${freighter.passportSerialNumber}</td>
                        <td>${freighter.educationSerialNumber}</td>
                        <td>${freighter.medSerialNumber}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.active == 'showFreighters'}">
    <div id="allFreightersModal" class="modal-table1">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <c:forEach var="name" items="${sessionScope.columnNames}">
                        <th>${name}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <!-- Здесь будут данные о работниках -->

                <c:forEach var="freighter" items="${sessionScope.freighters}">
                    <tr>
                        <td>${freighter.tax}</td>
                        <td>${freighter.sizeCost}</td>
                        <td>${freighter.weightCost}</td>
                        <td>${freighter.fragileCost}</td>
                        <td>${freighter.freighterName}</td>
                    </tr>
                </c:forEach>

                <!-- ... другие данные столбцов -->

                <!-- ... другие строки с данными о работниках -->
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<script>
    function showAllEmployees() {
        document.getElementById('allEmployeesModal').hidden = true;
        document.getElementById('allFreightersModal').hidden = true;
    }
    function showAllFreighters(){
        document.getElementById('allFreightersModal').hidden = true;
        document.getElementById('allEmployeesModal').hidden = true;
    }

    // function closeModal() {
    //     document.getElementById('allEmployeesModal').style.display = 'none';
    // }
    //
    // window.onclick = function(event) {
    //     if (event.target === document.getElementById('allEmployeesModal')) {
    //         closeModal();
    //     }
    // };
</script>
