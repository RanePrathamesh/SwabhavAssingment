<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login</title>

    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Google Font for modern look -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: #f7f2f8;
        }
        .login-container {
            max-width: 400px;
            margin: 80px auto;
            padding: 30px;
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }
        .form-label {
            font-weight: 500;
            color: #333;
        }
        .btn-purple {
            background-color: #6f42c1;
            color: #fff;
            border: none;
        }
        .btn-purple:hover {
            background-color: #5a32a3;
        }
        .error-msg {
            color: #d9534f;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="login-container">
        <h3 class="text-center mb-4" style="color:#6f42c1;">Login</h3>
        
        <form action="LoginServlet" method="post">
            <div class="mb-3">
                <label class="form-label">Username:</label>
                <input type="text" name="username" class="form-control" required>
            </div>

            <div class="mb-3	">
                <label class="form-label">Password:</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <div class="d-grid mb-3">
                <input type="submit" value="Login" class="btn btn-purple">
            </div>

            <c:if test="${not empty errorMsg}">
                <p class="error-msg">${errorMsg}</p>
            </c:if>
        </form>
         <p>Don't have an account? <a href="Register.jsp">Register Here</a></p>
    </div>
</div>

<% %>

</body>
</html>
