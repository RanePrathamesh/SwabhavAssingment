<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.aurionpro.model.User" %>
<%@ page import="com.aurionpro.model.Account" %>
<%
    // Get logged in user from session
    User user = (User) session.getAttribute("user");
    Account account = (Account) session.getAttribute("account"); 
    // (make sure you set account details in session after login/approval)
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">MyBank</a>
            <div class="d-flex">
                <span class="navbar-text text-white me-3">
                    Welcome, <%= user.getFirstName() %> <%= user.getLastName() %>
                </span>
                <a href="LogoutServlet" class="btn btn-outline-light btn-sm">Logout</a>
            </div>
        </div>
    </nav>

    <!-- Main Dashboard -->
    <div class="container mt-5">
        <div class="row mb-4">
            <div class="col text-center">
                <h2>Customer Dashboard</h2>
                <p class="text-muted">Manage your banking activities with ease</p>
            </div>
        </div>

        <!-- Account Info -->
        <div class="row justify-content-center mb-5">
            <div class="col-md-6">
                <div class="card shadow-sm text-center">
                    <div class="card-body">
                        <h5 class="card-title">Account Information</h5>
                        <p><strong>Account Number:</strong> 
                            <%= (account != null && account.getAccountNumber() != null) 
                                ? account.getAccountNumber() 
                                : "Pending Approval by Admin" %>
                        </p>
                        <p><strong>Email:</strong> <%= user.getEmail() %></p>
                        <p><strong>City:</strong> <%= user.getAddress() %></p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Action Menu -->
        <div class="row text-center g-4">
            <div class="col-md-4">
                <a href="BeneficiaryServlet" class="text-decoration-none">
                    <div class="card shadow-sm p-4">
                        <h5>➕ Add Beneficiaries</h5>
                        <p class="text-muted">Manage your recipients for transfers</p>
                    </div>
                </a>
            </div>
            <div class="col-md-4">
                <a href="PaymentServlet" class="text-decoration-none">
                    <div class="card shadow-sm p-4">
                        <h5>💳 Make Payment</h5>
                        <p class="text-muted">Transfer funds securely</p>
                    </div>
                </a>
            </div>
            <div class="col-md-4">
                <a href="ProfileServlet" class="text-decoration-none">
                    <div class="card shadow-sm p-4">
                        <h5>👤 View / Edit Profile</h5>
                        <p class="text-muted">Keep your information up to date</p>
                    </div>
                </a>
            </div>
            <div class="col-md-4">
                <a href="ViewBalanceServlet" class="text-decoration-none">
                    <div class="card shadow-sm p-4">
                        <h5>💰 View Balance</h5>
                        <p class="text-muted">Check your available funds</p>
                    </div>
                </a>
            </div>
            <div class="col-md-4">
                <a href="PassbookServlet" class="text-decoration-none">
                    <div class="card shadow-sm p-4">
                        <h5>📘 View Passbook</h5>
                        <p class="text-muted">See your transaction history</p>
                    </div>
                </a>
            </div>
        </div>
    </div>
<% %>
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
