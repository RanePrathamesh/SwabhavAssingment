<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.aurionpro.model.Beneficiary"%>
<%@ page import="com.aurionpro.model.Account"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>💳 Make a Payment | SecureBank</title>

    <!-- Bootstrap 5 CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f1f3f5;
        }

        .card {
            border-radius: 16px;
        }

        .card-header {
            border-top-left-radius: 16px;
            border-top-right-radius: 16px;
        }

        .alert-info {
            font-size: 1.1rem;
            background-color: #e9f7ff;
            color: #0275d8;
        }

        .form-label {
            font-weight: 500;
        }

        .btn-success {
            font-weight: bold;
            font-size: 1rem;
        }

        .balance-text {
            font-size: 1.2rem;
        }
    </style>
</head>
<body>

<%
    Account account = (Account) request.getAttribute("account");
    List<Beneficiary> beneficiaries = (List<Beneficiary>) request.getAttribute("beneficiaries");
    String message = (String) request.getAttribute("message");
%>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">

            <div class="card shadow-lg">
                <div class="card-header bg-primary text-white text-center">
                    <h4>💸 Make a Payment</h4>
                </div>

                <div class="card-body">

                    <% if (message != null) { %>
                        <div class="alert alert-success fw-bold text-center">
                            ✅ <%=message %>
                        </div>
                        <div class="text-center mb-3">
                            <a href="CustomerDashboardServlet" class="btn btn-outline-primary">🏠 Back to Dashboard</a>
                        </div>
                    <% } %>

                    <!-- Show Account Balance -->
                    <div class="alert alert-info text-center balance-text fw-bold mb-4">
                        💰 Current Balance: ₹ <%=account.getBalance()%>
                    </div>

                    <% if (beneficiaries == null || beneficiaries.isEmpty()) { %>
                        <div class="alert alert-warning text-center">
                            ⚠️ No beneficiaries found. <a href="BeneficiaryServlet" class="alert-link">Add a beneficiary</a> first.
                        </div>
                    <% } else { %>

                        <!-- Payment Form -->
                        <form action="PaymentServlet" method="post" class="needs-validation" novalidate>

                            <!-- Beneficiary Dropdown -->
                            <div class="mb-3">
                                <label class="form-label">👤 Select Beneficiary</label>
                                <select name="beneficiaryId" class="form-select" required>
                                    <option value="" disabled selected>-- Choose Beneficiary --</option>
                                    <% for (Beneficiary b : beneficiaries) { %>
                                        <option value="<%=b.getBeneficiaryId()%>">
                                            <%=b.getNickname() != null ? b.getNickname() : b.getBeneficiaryName()%>
                                            - <%=b.getAccountNumber()%>
                                        </option>
                                    <% } %>
                                </select>
                                <div class="invalid-feedback">Please select a beneficiary.</div>
                            </div>

                            <!-- Amount -->
                            <div class="mb-3">
                                <label class="form-label">💵 Enter Amount</label>
                                <input type="number" step="0.01" min="1" max="<%=account.getBalance()%>" name="amount"
                                       class="form-control" placeholder="e.g. 1000.00" required>
                                <div class="invalid-feedback">Please enter a valid amount.</div>
                            </div>

                            <!-- Submit -->
                            <div class="d-grid mt-4">
                                <button type="submit" class="btn btn-success">✅ Proceed to Pay</button>
                            </div>
                        </form>
                    <% } %>
                </div>
            </div>

        </div>
    </div>
</div>

<!-- Bootstrap Form Validation Script -->
<script>
    (function () {
        'use strict'
        const forms = document.querySelectorAll('.needs-validation');
        Array.from(forms).forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    })();
</script>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
