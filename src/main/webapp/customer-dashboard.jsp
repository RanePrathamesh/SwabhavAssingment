<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.User" %>
<%@ page import="com.aurionpro.model.Account" %>

<%
    User user = (User) request.getAttribute("user");
    Account account = (Account) request.getAttribute("account");
    String rejectionReason = (String) request.getAttribute("rejectionReason");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SecureBank | Customer Dashboard</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: #f4f6f9;
        }

        .navbar-brand {
            font-weight: bold;
            font-size: 1.5rem;
            color: #f8f9fa !important;
        }

        .dashboard-heading {
            color: #343a40;
        }

        .card {
            border: none;
            border-radius: 12px;
            transition: transform 0.3s;
        }

        .card:hover {
            transform: scale(1.03);
        }

        .card-title {
            font-weight: 600;
            color: #6f42c1;
        }

        .card-body p {
            margin: 5px 0;
        }

        .text-muted {
            font-size: 0.9rem;
        }

        .action-card {
            background-color: #ffffff;
            cursor: pointer;
        }

        .action-card h5 {
            font-weight: 600;
        }

        .action-card:hover {
            background-color: #f0f0ff;
        }

        .alert {
            font-size: 1rem;
            font-weight: 500;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">🏦 SecureBank</a>
        <div class="d-flex align-items-center">
            <span class="navbar-text text-white me-3">
                👋 Welcome, <%= user != null ? user.getFirstName() + " " + user.getLastName() : "Guest" %>
            </span>
            <a href="LogoutServlet" class="btn btn-outline-light btn-sm">Logout</a>
        </div>
    </div>
</nav>

<!-- Main Dashboard -->
<div class="container mt-5">
    <div class="text-center mb-4">
        <h2 class="dashboard-heading">Customer Dashboard</h2>
        <p class="text-muted">Effortlessly manage your banking experience</p>
    </div>

<!-- Account Info Card -->
<div class="row justify-content-center mb-4">
    <div class="col-md-6">
        <div class="card shadow-sm text-center p-4">
            <h5 class="card-title mb-3">🔐 Account Information</h5>

            <% if (account != null && account.isApproved()) { %>
               
                <p><strong>Account Number:</strong><br>
                    <%= account.getAccountNumber() %>
                </p>
                <p><strong>Email:</strong> <%= user != null ? user.getEmail() : "N/A" %></p>
                <p><strong>City:</strong> <%= user != null ? user.getAddress() : "N/A" %></p>
            <% } else if (rejectionReason != null && !rejectionReason.trim().isEmpty()) { %>
               
                <div class="alert alert-danger mt-3">
                    🚫 <strong>Account Rejected:</strong> <%= rejectionReason %>
                </div>
            <% } else { %>
                
                <p><strong>Account Status:</strong><br>
                    <span class="text-warning">⏳ Pending Approval by Admin</span>
                </p>
                <p><strong>Email:</strong> <%= user != null ? user.getEmail() : "N/A" %></p>
                <p><strong>City:</strong> <%= user != null ? user.getAddress() : "N/A" %></p>
            <% } %>
        </div>
    </div>
</div>



    <% if (account != null && account.getAccountNumber() != null 
           && !account.getAccountNumber().toUpperCase().startsWith("PENDING")) { %>

    <!-- Action Cards -->
    <div class="row g-4 text-center">
        <div class="col-md-4">
            <a href="BeneficiaryServlet" class="text-decoration-none">
                <div class="card action-card shadow-sm p-4">
                    <h5>👥 Add Beneficiaries</h5>
                    <p class="text-muted">Manage recipients for quick transfers</p>
                </div>
            </a>
        </div>

        <div class="col-md-4">
            <a href="PaymentServlet" class="text-decoration-none">
                <div class="card action-card shadow-sm p-4">
                    <h5>💸 Make Payment</h5>
                    <p class="text-muted">Transfer money safely & instantly</p>
                </div>
            </a>
        </div>

        <div class="col-md-4">
            <a href="ProfileServlet" class="text-decoration-none">
                <div class="card action-card shadow-sm p-4">
                    <h5>👤 Profile</h5>
                    <p class="text-muted">View or update your details</p>
                </div>
            </a>
        </div>

        <div class="col-md-4">
            <a href="ViewBalanceServlet" class="text-decoration-none">
                <div class="card action-card shadow-sm p-4">
                    <h5>💰 View Balance</h5>
                    <p class="text-muted">Check your current account balance</p>
                </div>
            </a>
        </div>

        <div class="col-md-4">
            <a href="PassbookServlet" class="text-decoration-none">
                <div class="card action-card shadow-sm p-4">
                    <h5>📘 Passbook</h5>
                    <p class="text-muted">Transaction history & statements</p>
                </div>
            </a>
        </div>
    </div>

    <% } else { %>
    <!-- Pending Approval Alert -->
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <div class="alert alert-warning text-center shadow-sm mt-4">
                ⚠️ Your account is <strong>pending approval</strong> by the administrator.
            </div>
        </div>
    </div>
    
    <% if (rejectionReason != null) { %>
    <!-- Rejection Reason -->
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <div class="alert alert-danger text-center shadow-sm mt-4">
                🚫 Your account was rejected. Reason: <strong><%= rejectionReason %></strong>
            </div>
        </div>
    </div>
    <% } %>
    <% } %>
</div>

<!-- Bootstrap Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
