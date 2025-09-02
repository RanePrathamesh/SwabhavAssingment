<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    com.aurionpro.model.User user = (com.aurionpro.model.User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-7">
            <div class="card shadow-lg border-0 rounded-3">
                <div class="card-header bg-primary text-white text-center fs-4 fw-semibold">
                    Edit Profile
                </div>
                <div class="card-body p-4">

                    <form action="ProfileServlet" method="post">
                        <!-- First Name -->
                        <div class="mb-3">
                            <label class="form-label">First Name</label>
                            <input type="text" name="firstName" class="form-control" value="${sessionScope.user.firstName}" required>
                        </div>

                        <!-- Last Name -->
                        <div class="mb-3">
                            <label class="form-label">Last Name</label>
                            <input type="text" name="lastName" class="form-control" value="${sessionScope.user.lastName}" required>
                        </div>

                        <!-- Email -->
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" value="${sessionScope.user.email}" required>
                        </div>

                        <!-- Mobile -->
                        <div class="mb-3">
                            <label class="form-label">Mobile Number</label>
                            <input type="tel" name="mobileNumber" class="form-control" value="${sessionScope.user.mobileNumber}" pattern="[0-9]{10}">
                        </div>

                        <!-- Age -->
                        <div class="mb-3">
                            <label class="form-label">Age</label>
                            <input type="number" name="age" class="form-control" min="18" max="120" required value="${sessionScope.user.age}">
                        </div>

                        <!-- Gender -->
                        <div class="mb-3">
                            <label class="form-label">Gender</label>
                            <select name="gender" class="form-select" required>
                                <option value="Male" ${sessionScope.user.gender == 'Male' ? 'selected' : ''}>Male</option>
                                <option value="Female" ${sessionScope.user.gender == 'Female' ? 'selected' : ''}>Female</option>
                                <option value="Other" ${sessionScope.user.gender == 'Other' ? 'selected' : ''}>Other</option>
                            </select>
                        </div>

                        <!-- Address -->
                        <div class="mb-3">
                            <label class="form-label">Address</label>
                            <textarea name="address" class="form-control" rows="2">${sessionScope.user.address}</textarea>
                        </div>

                        <!-- Buttons -->
                        <div class="d-flex justify-content-between">
                            <a href="CustomerDashboardServlet" class="btn btn-outline-primary btn-sm">⬅ Back to Dashboard</a>
                            <div>
                                <a href="change-password.jsp" class="btn btn-warning btn-sm me-2">🔑 Change Password</a>
                                <button type="submit" class="btn btn-success btn-sm">💾 Update Profile</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
