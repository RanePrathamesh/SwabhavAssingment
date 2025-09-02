<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Secure Bank Login</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(to right, #dfe9f3, #ffffff);
            height: 100vh;
        }

        .login-container {
            max-width: 450px;
            margin: 80px auto;
            padding: 30px 40px;
            background: #ffffff;
            border-radius: 15px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
        }

        .login-title {
            font-size: 24px;
            color: #6f42c1;
            font-weight: 700;
            text-align: center;
            margin-bottom: 20px;
        }

        .login-title span {
            font-size: 32px;
        }

        .form-label {
            font-weight: 500;
            color: #333;
        }

        .btn-purple {
            background-color: #6f42c1;
            color: #fff;
            transition: all 0.3s ease;
        }

        .btn-purple:hover {
            background-color: #5a32a3;
        }

        .error-msg {
            color: #dc3545;
            font-weight: 500;
            margin-top: 10px;
        }

        .bank-note {
            font-size: 13px;
            text-align: center;
            color: #888;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="login-container">

        <div class="login-title">
            🏦 <br>
            Welcome to <span>SecureBank</span>
        </div>

        <form action="LoginServlet" method="post" autocomplete="off">
            <div class="mb-3">
                <label class="form-label">👤 Username</label>
                <input type="text" name="username" class="form-control" required autofocus>
            </div>

            <div class="mb-3">
                <label class="form-label">🔐 Password</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <div class="d-grid mb-2">
                <button type="submit" class="btn btn-purple">Login</button>
            </div>

            <c:if test="${not empty errorMsg}">
                <div class="error-msg">${errorMsg}</div>
            </c:if>
        </form>

        <div class="text-center mt-3">
            Don't have an account? <a href="Register.jsp" class="text-decoration-none">Register here</a>
        </div>

        <div class="bank-note">
            Admin login? Use provided credentials by admin.
        </div>
    </div>
</div>

</body>
</html>
