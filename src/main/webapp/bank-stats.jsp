<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Bank Stats - Total Balance</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="admin-dashboard.jsp">MyBank Admin</a>
        <a href="LogoutServlet" class="btn btn-outline-light btn-sm">Logout</a>
    </div>
</nav>

<div class="container mt-5">
    <div class="card shadow p-5">
        <h2 class="mb-4">🏦 Total Bank Balance</h2>
        <h3>
    ₹ <%= String.format("%,.2f", request.getAttribute("totalBalance")) %>
</h3>

        <p class="text-muted">This is the total cash held by all approved accounts in the system.</p>
        <a href="admin-dashboard.jsp" class="btn btn-primary mt-3">Back to Dashboard</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
