<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>💰 View Balance | SecureBank</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(to right, #f8f9fa, #e9ecef);
            font-family: 'Roboto', sans-serif;
        }

        .card {
            border-radius: 20px;
        }

        .card-header {
            border-top-left-radius: 20px;
            border-top-right-radius: 20px;
            background: #007bff;
            color: white;
        }

        .balance-icon {
            font-size: 2.5rem;
        }

        .balance-amount {
            font-size: 2.5rem;
            color: #28a745;
            font-weight: 600;
        }

        .btn-outline-primary {
            border-radius: 20px;
            font-weight: 500;
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">

            <div class="card shadow-lg">
                <div class="card-header text-center">
                    <h4>💰 Your Account Balance</h4>
                </div>
                <div class="card-body text-center">
                    <div class="balance-icon mb-3">📟</div>
                    <div class="balance-amount">₹ ${balance}</div>
                    <p class="text-muted mt-2">As of today</p>
                </div>
                <div class="card-footer text-center">
                    <a href="CustomerDashboardServlet" class="btn btn-outline-primary btn-sm">
                        ⬅️ Back to Dashboard
                    </a>
                </div>
            </div>

        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
