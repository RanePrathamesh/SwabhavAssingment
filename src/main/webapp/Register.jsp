<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SecureBank - Register</title>

<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap"
	rel="stylesheet">

<style>
body {
	font-family: 'Roboto', sans-serif;
	background: linear-gradient(to right, #f5f7fa, #c3cfe2);
}

.card {
	border-radius: 15px;
}

.card-header {
	background-color: #6f42c1;
}

.card-header h3 {
	margin: 0;
	font-weight: 600;
}

.form-label {
	font-weight: 500;
}

.btn-success {
	background-color: #198754;
	font-weight: 500;
}

.btn-success:hover {
	background-color: #157347;
}

.bank-icon {
	font-size: 28px;
	margin-right: 10px;
}

.login-link {
	margin-top: 10px;
	text-align: center;
}

.login-link a {
	text-decoration: none;
	color: #6f42c1;
	font-weight: 500;
}

.login-link a:hover {
	text-decoration: underline;
}

.alert {
	font-weight: 500;
}
</style>
</head>
<body>

	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6">
				<div class="card shadow">
					<div class="card-header text-white text-center">
						<div class="d-flex justify-content-center align-items-center">
							<span class="bank-icon">🏦</span>
							<h3>User Registration</h3>
						</div>
					</div>
					<div class="card-body">
						<!-- Show validation/success message -->
						<c:if test="${not empty message}">
							<div class="alert alert-info text-center">${message}</div>
						</c:if>

						<form action="RegisterServlet" method="post" enctype="multipart/form-data">
							<div class="mb-3">
								<label for="firstName" class="form-label">👤 First Name</label>
								<input type="text" name="firstName" class="form-control"
									required>
							</div>

							<div class="mb-3">
								<label for="lastName" class="form-label">👤 Last Name</label> <input
									type="text" name="lastName" class="form-control" required>
							</div>

							<div class="mb-3">
								<label for="email" class="form-label">📧 Email</label> <input
									type="email" name="email" class="form-control" required>
							</div>

							<div class="mb-3">
								<label for="mobileNumber" class="form-label">📱Mobile
									Number</label> <input type="text" name="mobileNumber"
									class="form-control" maxlength ="10" required>
							</div>

							<div class="mb-3">
								<label for="age" class="form-label"> Age</label> <input
									type="number" name="age" class="form-control" min="18" required>
							</div>

							<div class="mb-3">
								<label for="gender" class="form-label"> Gender</label> <select
									name="gender" class="form-select" required>
									<option value="">--Select--</option>
									<option value="Male">Male</option>
									<option value="Female">Female</option>
									<option value="Other">Other</option>
								</select>
							</div>

							<div class="mb-3">
								<label for="address" class="form-label"> Address</label> <input
									type="text" name="address" class="form-control" required>
							</div>

							<!-- ✅ Aadhar Number -->
							<div class="mb-3">
								<label for="aadharNumber" class="form-label">🆔 Aadhar
									Number</label> <input type="text" name="aadharNumber"
									class="form-control"pattern="[0-9]{12}" maxlength="12" required
									title="Enter a valid 12-digit Aadhar number">
							</div>


							<!-- ✅ Aadhar Document Upload -->
							<div class="mb-3">
								<label for="aadharFile" class="form-label">📄 Upload
									Aadhar Document</label> <input type="file" name="aadharFile"
									class="form-control" accept=".pdf,.jpg,.jpeg,.png" required>
								<small class="text-muted">Accepted formats:  JPG,
									PNG(upto 5MB)</small>
							</div>

							<div class="d-grid">
								<button type="submit" class="btn btn-success">Register</button>
							</div>
						</form>

						<div class="login-link">
							Already registered? <a href="Login.jsp">Login here</a>
						</div>
					</div>
					<div class="card-footer text-muted text-center">&copy; 2025
						SecureBank</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
