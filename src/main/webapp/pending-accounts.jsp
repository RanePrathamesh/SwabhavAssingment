<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pending Accounts</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body class="bg-light">

	<div class="container mt-5">
		<h2 class="text-center mb-4">Pending Account Approvals</h2>

		<!-- Display Message -->
		<c:if test="${not empty msg}">
			<div class="alert alert-info text-center" role="alert">${msg}</div>
		</c:if>

		<!-- Display Table if there are pending accounts -->
		<c:choose>
			<c:when test="${empty pendingAccounts}">
				<div class="alert alert-warning text-center" role="alert">No
					pending requests.</div>
			</c:when>
			<c:otherwise>
				<div class="card shadow-sm">
					<div class="card-body">
						<table
							class="table table-hover table-bordered align-middle text-center">
							<thead class="table-dark">
								<tr>
									<th>Account ID</th>
									<th>User ID</th>
									<th>Name</th>
									<th>Email</th>
									<th>Aadhar</th>
									<th>Request Date</th>
					
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="acc" items="${pendingAccounts}">
									<tr>
										<td>${acc.accountId}</td>
										<td>${acc.userId}</td>
										<td>${acc.firstName}${acc.lastName}</td>
										<td>${acc.email}</td>
										<td>${acc.aadharNumber} <br> <!-- Display Aadhar Image if exists -->
											<c:if test="${not empty acc.aadharFilePath}">
												<img src="${acc.aadharFilePath}" alt="Aadhar Card"
													style="max-width: 100px; max-height: 100px; cursor: pointer;"
													data-bs-toggle="modal"
													data-bs-target="#aadharModal${acc.accountId}" />
											</c:if>
										</td>
										<td>${acc.createdAt}</td>

										


										<td>
											<!-- Approve Form -->
											<form method="post" action="PendingAccountsServlet"
												class="d-inline me-1">
												<input type="hidden" name="accountId"
													value="${acc.accountId}" /> <input type="hidden"
													name="action" value="approve" />
												<button type="submit" class="btn btn-success btn-sm"
													title="Approve Account">✅ Approve</button>
											</form> <!-- Reject Form with Reason -->
											<form method="post" action="PendingAccountsServlet"
												class="d-inline">
												<input type="hidden" name="accountId"
													value="${acc.accountId}" /> <input type="hidden"
													name="action" value="reject" />

												<!-- Rejection Reason Text Area -->
												<div class="mb-3">
													<label for="rejectionReason${acc.accountId}"
														class="form-label">Rejection Reason</label>
													<textarea class="form-control"
														id="rejectionReason${acc.accountId}"
														name="rejectionReason" rows="2" required></textarea>
												</div>

												<button type="submit" class="btn btn-danger btn-sm"
													title="Reject Account">❌ Reject</button>
											</form>
										</td>
									</tr>

									<!-- Modal for Aadhar Image -->
									<div class="modal fade" id="aadharModal${acc.accountId}"
										tabindex="-1"
										aria-labelledby="aadharModalLabel${acc.accountId}"
										aria-hidden="true">
										<div class="modal-dialog modal-dialog-centered modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<h5 class="modal-title"
														id="aadharModalLabel${acc.accountId}">Aadhar Document</h5>
													<button type="button" class="btn-close"
														data-bs-dismiss="modal" aria-label="Close"></button>
												</div>
												<div class="modal-body text-center">
													<img src="${acc.aadharFilePath}" alt="Aadhar Card"
														class="img-fluid" style="max-height: 500px;" />
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<!-- Back Button -->
	<div class="container mt-4 text-center">
		<a href="admin-dashboard.jsp" class="btn btn-outline-secondary">Back
			to Dashboard</a>
	</div>

	<!-- Bootstrap JS (for modal and other features) -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
