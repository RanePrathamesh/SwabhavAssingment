<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.User"%>

<%
    User admin = (User) session.getAttribute("user");
    if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Admin Dashboard</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />

    <style>
        body {
            background-color: #f0f2f5;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        nav.navbar {
            box-shadow: 0 2px 5px rgb(0 0 0 / 0.1);
        }

        .dashboard-header {
            margin-top: 40px;
            margin-bottom: 40px;
        }

        .card {
            cursor: pointer;
            transition: all 0.25s ease-in-out;
            border-radius: 0.75rem;
            box-shadow: 0 4px 8px rgb(0 0 0 / 0.1);
        }

        .card:hover {
            background-color: #e9f0ff;
            transform: translateY(-5px);
            box-shadow: 0 8px 15px rgb(0 0 0 / 0.15);
            text-decoration: none !important;
        }

        .card-icon {
            font-size: 2.5rem;
            color: #0d6efd;
            margin-bottom: 12px;
        }

        .footer {
            margin-top: auto;
            background-color: #343a40;
            color: white;
            padding: 15px 0;
            text-align: center;
            font-size: 0.9rem;
        }

        .welcome-text {
            font-weight: 600;
            font-size: 1.1rem;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-dark bg-primary shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">Secure Bank Admin Panel</a>
            <div class="d-flex align-items-center gap-3">
                <span class="text-white welcome-text">
                    Welcome, <%= admin.getFirstName() %> <%= admin.getLastName() %>
                </span>
                <a href="LogoutServlet" class="btn btn-outline-light btn-sm">
                    <i class="bi bi-box-arrow-right"></i> Logout
                </a>
            </div>
        </div>
    </nav>

    <!-- Main Container -->
    <main class="container">
        <h2 class="text-center dashboard-header fw-bold text-primary">Admin Dashboard</h2>

        <div class="row g-4 justify-content-center">

            <!-- Pending Requests -->
            <div class="col-sm-6 col-md-4 col-lg-3">
                <a href="PendingAccountsServlet" class="text-decoration-none text-dark">
                    <div class="card text-center p-4 h-100">
                        <i class="bi bi-pencil-square card-icon"></i>
                        <h5 class="fw-semibold">Pending Requests</h5>
                        <p class="text-muted small">Approve new customer accounts</p>
                    </div>
                </a>
            </div>

            <!-- Manage Users -->
            <div class="col-sm-6 col-md-4 col-lg-3">
                <a href="ManageUsersServlet" class="text-decoration-none text-dark">
                    <div class="card text-center p-4 h-100">
                        <i class="bi bi-people card-icon"></i>
                        <h5 class="fw-semibold">Manage Users</h5>
                        <p class="text-muted small">Activate or deactivate customer access</p>
                    </div>
                </a>
            </div>

            <!-- Total Bank Balance -->
            <div class="col-sm-6 col-md-4 col-lg-3">
                <a href="BankStatsServlet" class="text-decoration-none text-dark">
                    <div class="card text-center p-4 h-100">
                        <i class="bi bi-cash-stack card-icon"></i>
                        <h5 class="fw-semibold">Bank Balance</h5>
                        <p class="text-muted small">View total cash in the system</p>
                    </div>
                </a>
            </div>

            <!-- View All Transactions -->
            <div class="col-sm-6 col-md-4 col-lg-3">
                <a href="AllTransactionsServlet" class="text-decoration-none text-dark">
                    <div class="card text-center p-4 h-100">
                        <i class="bi bi-journal-text card-icon"></i>
                        <h5 class="fw-semibold">All Transactions</h5>
                        <p class="text-muted small">Audit logs and transaction filters</p>
                    </div>
                </a>
            </div>
            
             <div class="col-sm-6 col-md-4 col-lg-3">
                <a href="RejectedApprovalServlet" class="text-decoration-none text-dark">
                    <div class="card text-center p-4 h-100">
                        <i class="bi bi-journal-text card-icon"></i>
                        <h5 class="fw-semibold">Rejected Users</h5>
                        <p class="text-muted small">Manage Delete User</p>
                    </div>
                </a>
            </div>

        </div>

       
    </main>

    <!-- Footer -->
    <footer class="footer">
        &copy; 2025 MyBank Admin Dashboard
    </footer>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
