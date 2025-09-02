<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Change Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-lg border-0 rounded-4">
                <div class="card-header bg-primary text-white text-center rounded-top-4">
                    <h4>Change Password</h4>
                </div>
                <div class="card-body">
                    <form action="ChangePasswordServlet" method="post">

                        <div class="mb-3">
                            <label for="currentPassword" class="form-label">Current Password</label>
                            <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                        </div>

                        <div class="mb-3">
                            <label for="newPassword" class="form-label">New Password</label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                            <div class="form-text">
                                Must be at least 8 characters with uppercase, lowercase, number, and special character.
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary w-100 rounded-pill">Update Password</button>
                        <a href="customer-dashboard.jsp" class="btn btn-outline-secondary w-100 mt-2 rounded-pill">Back to Dashboard</a>
                    </form>

                    <% if (request.getAttribute("msg") != null) { %>
                        <div class="alert alert-info mt-3 text-center">
                            <%= request.getAttribute("msg") %>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
