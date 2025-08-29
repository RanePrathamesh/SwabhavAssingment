<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.aurionpro.model.Beneficiary"%>
<%@ page import="com.aurionpro.model.Account"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Make Payment</title>
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">

                <div class="card shadow-lg rounded-4">
                    <div class="card-header text-center bg-primary text-white rounded-top-4">
                        <h4>Make a Payment</h4>
                    </div>
                    <div class="card-body">

                        <%
                        Account account = (Account) session.getAttribute("account");
                        List<Beneficiary> beneficiaries = (List<Beneficiary>) request.getAttribute("beneficiaries");
                        %>

                        <% 
                        // Message for success or failure
                        String message = (String) request.getAttribute("message");
                        if (message != null) { 
                        %>
                        <div class="alert alert-success text-center fw-bold">
                            <%=message%>
                        </div>
                        <div class="text-center mb-3">
                            <a href="customer-dashboard.jsp" class="btn btn-outline-primary fw-bold">Back to Dashboard</a>
                        </div>
                        <% 
                        }
                        %>

                        <!-- Show Balance -->
                        <div class="alert alert-info text-center fw-bold">
                            Available Balance: ₹<%=account.getBalance()%>
                        </div>

                        <!-- If no beneficiaries -->
                        <% 
                        if (beneficiaries == null || beneficiaries.isEmpty()) { 
                        %>
                        <div class="alert alert-warning text-center">
                            No beneficiaries found. <a href="BeneficiaryServlet" class="alert-link">Add a beneficiary</a> to continue.
                        </div>
                        <% 
                        } else { 
                        %>

                        <!-- Payment Form -->
                        <form action="PaymentServlet" method="post" class="needs-validation" novalidate>

                            <!-- Beneficiary Dropdown -->
                            <div class="mb-3">
                                <label class="form-label fw-bold">Select Beneficiary</label> 
                                <select name="beneficiaryId" class="form-select" required>
                                    <option value="" disabled selected>-- Choose Beneficiary --</option>
                                    <%
                                    for (Beneficiary b : beneficiaries) {
                                    %>
                                    <option value="<%=b.getBeneficiaryId()%>">
                                        <%=b.getNickname() != null ? b.getNickname() : b.getBeneficiaryName()%>
                                        - <%=b.getAccountNumber()%>
                                    </option>
                                    <%
                                    }
                                    %>
                                </select>
                                <div class="invalid-feedback">Please select a beneficiary</div>
                            </div>

                            <!-- Amount Field -->
                            <div class="mb-3">
                                <label class="form-label fw-bold">Enter Amount</label> 
                                <input type="number" step="0.01" min="1" max="<%=account.getBalance()%>" name="amount"
                                    class="form-control" placeholder="Enter amount" required>
                                <div class="invalid-feedback">Please enter a valid amount</div>
                            </div>

                            <!-- Submit Button -->
                            <div class="d-grid">
                                <button type="submit" class="btn btn-success rounded-3 fw-bold">Proceed to Pay</button>
                            </div>

                        </form>
                        <% 
                        }
                        %>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <!-- Bootstrap validation script -->
    <script>
        (function() {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.prototype.slice.call(forms).forEach(function(form) {
                form.addEventListener('submit', function(event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
        })()
    </script>

</body>
</html>
