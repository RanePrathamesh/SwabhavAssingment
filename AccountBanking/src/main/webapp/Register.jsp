<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>User Registration</title>
    <!-- Bootstrap CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow">
                <div class="card-header bg-primary text-white text-center">
                    <h3>User Registration</h3>
                </div>
                <div class="card-body">
                    <!-- Show validation/success message -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-info">${message}</div>
                    </c:if>

                    <form action="RegisterServlet" method="post">
                        <div class="mb-3">
                            <label for="firstName" class="form-label">First Name:</label>
                            <input type="text" name="firstName" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="lastName" class="form-label">Last Name:</label>
                            <input type="text" name="lastName" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email:</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="mobileNumber" class="form-label">Mobile Number:</label>
                            <input type="text" name="mobileNumber" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="age" class="form-label">Age:</label>
                            <input type="number" name="age" class="form-control" min="0" required>
                        </div>

                        <div class="mb-3">
                            <label for="gender" class="form-label">Gender:</label>
                            <select name="gender" class="form-select" required>
                                <option value="">--Select--</option>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Other">Other</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="address" class="form-label">Address:</label>
                            <input type="text" name="address" class="form-control" required>
                        </div>

                        <div class="d-grid">
                            <input type="submit" value="Register" class="btn btn-success">
                        </div>
                    </form>
                </div>
                <div class="card-footer text-muted text-center">
                    &copy; 2025 Registration System
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle (Optional for dropdowns, etc.) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
