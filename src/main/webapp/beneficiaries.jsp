<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.aurionpro.model.Beneficiary" %>

<!DOCTYPE html>
<html>
<head>
    <title>👥 My Beneficiaries | SecureBank</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f8f9fa;
        }

        h2 {
            font-weight: bold;
            color: #343a40;
        }

        .card {
            border-radius: 12px;
        }

        .table th, .table td {
            vertical-align: middle;
        }

        .btn-sm {
            font-size: 0.8rem;
        }

        .modal-header {
            background-color: #0d6efd;
            color: white;
        }

        .form-label {
            font-weight: 500;
        }

        .table-dark th {
            background-color: #343a40 !important;
        }

        .alert {
            font-weight: 500;
        }

        .add-beneficiary-header {
            font-weight: bold;
            font-size: 1.1rem;
        }
    </style>
</head>
<body>

<div class="container mt-5">

    <!-- Success / Error Alerts -->
    <c:if test="${not empty param.success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ✅ ${param.success}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ❌ ${param.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Page Heading -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>👥 My Beneficiaries</h2>
        <a href="CustomerDashboardServlet" class="btn btn-outline-primary">🏠 Back to Dashboard</a>
    </div>

    <!-- Beneficiary List Table -->
    <c:if test="${empty beneficiaries}">
        <p class="text-muted">No beneficiaries added yet. Add one below. 👇</p>
    </c:if>

    <c:if test="${not empty beneficiaries}">
        <div class="table-responsive">
            <table class="table table-bordered table-hover bg-white shadow-sm">
                <thead class="table-dark">
                <tr>
                    <th>👤 Name</th>
                    <th>💳 Account No.</th>
                    <th>🏦 Bank</th>
                    <th>🔢 IFSC</th>
                    <th>🏷️ Nickname</th>
                    <th>📅 Added On</th>
                    <th>⚙️ Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="b" items="${beneficiaries}">
                    <tr>
                        <td>${b.beneficiaryName}</td>
                        <td>${b.accountNumber}</td>
                        <td>${b.bankName}</td>
                        <td>${b.ifscCode}</td>
                        <td>${b.nickname}</td>
                        <td><fmt:formatDate value="${b.createdAt}" pattern="dd MMM yyyy HH:mm"/></td>
                        <td>
                            <!-- Delete -->
                            <form method="post" action="BeneficiaryServlet" class="d-inline">
                                <input type="hidden" name="action" value="delete"/>
                                <input type="hidden" name="beneficiaryId" value="${b.beneficiaryId}"/>
                                <button type="submit" class="btn btn-danger btn-sm"
                                        onclick="return confirm('Are you sure you want to delete this beneficiary?');">
                                    🗑️ Delete
                                </button>
                            </form>

                            <!-- Edit -->
                            <button class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                    data-bs-target="#editModal${b.beneficiaryId}">
                                ✏️ Edit
                            </button>

                            <!-- Edit Modal -->
                            <div class="modal fade" id="editModal${b.beneficiaryId}" tabindex="-1"
                                 aria-labelledby="editModalLabel${b.beneficiaryId}" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <form method="post" action="BeneficiaryServlet">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="editModalLabel${b.beneficiaryId}">Edit Beneficiary</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <input type="hidden" name="action" value="update"/>
                                                <input type="hidden" name="beneficiaryId" value="${b.beneficiaryId}"/>

                                                <div class="mb-3">
                                                    <label class="form-label">Name</label>
                                                    <input type="text" name="beneficiaryName" class="form-control" value="${b.beneficiaryName}" required/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Account Number</label>
                                                    <input type="text" name="accountNumber" class="form-control" value="${b.accountNumber}" required/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Bank Name</label>
                                                    <input type="text" name="bankName" class="form-control" value="${b.bankName}" required/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">IFSC Code</label>
                                                    <input type="text" name="ifscCode" class="form-control" value="${b.ifscCode}" required/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Nickname</label>
                                                    <input type="text" name="nickname" class="form-control" value="${b.nickname}"/>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <!-- Add New Beneficiary Form -->
    <div class="card shadow-sm mt-5">
        <div class="card-header bg-primary text-white add-beneficiary-header">
            ➕ Add New Beneficiary
        </div>
        <div class="card-body">
            <form method="post" action="BeneficiaryServlet">
                <input type="hidden" name="action" value="add"/>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Beneficiary Name</label>
                        <input type="text" name="beneficiaryName" class="form-control" required/>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Account Number</label>
                        <input type="text" name="accountNumber" class="form-control" required/>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Bank Name</label>
                        <input type="text" name="bankName" class="form-control" required/>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">IFSC Code</label>
                        <input type="text" name="ifscCode" class="form-control" required/>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Nickname (Optional)</label>
                    <input type="text" name="nickname" class="form-control"/>
                </div>

                <button type="submit" class="btn btn-success">
                    💾 Add Beneficiary
                </button>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
