<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pending Accounts</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container mt-5">
        <h2 class="text-center mb-4">Pending Account Approvals</h2>

        <c:if test="${not empty msg}">
            <div class="alert alert-info text-center" role="alert">
                ${msg}
            </div>
        </c:if>

        <div class="card shadow-sm">
            <div class="card-body">
                <table class="table table-hover table-bordered align-middle text-center">
                    <thead class="table-dark">
                        <tr>
                            <th>Account ID</th>
                            <th>User ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Created At</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="acc" items="${pendingAccounts}">
                            <tr>
                                <td>${acc.accountId}</td>
                                <td>${acc.userId}</td>
                                <td>${acc.firstName} ${acc.lastName}</td>
                                <td>${acc.email}</td>
                                <td>${acc.createdAt}</td>
                                <td>
                                    <form method="post" action="AdminServlet" class="d-inline">
                                        <input type="hidden" name="accountId" value="${acc.accountId}" />
                                        <button type="submit" class="btn btn-success btn-sm">
                                            ✅ Approve
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<a href="Login.jsp" >Logout</a>
    <!-- Bootstrap JS (optional, for interactivity like alerts/modal) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
