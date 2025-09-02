<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Manage Users - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        body {
            background-color: #f8f9fa;
        }
        h2 {
            font-weight: 700;
            margin-bottom: 30px;
            color: #0d6efd;
        }
        .table thead th {
            vertical-align: middle;
        }
        .btn-sm {
            min-width: 90px;
        }
        .modal-header {
            background-color: #ffc107;
            color: #212529;
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="container mt-5 mb-5">

    <h2 class="text-center">Manage Users</h2>

    <c:if test="${not empty msg}">
        <div class="alert alert-info alert-dismissible fade show" role="alert">
            ${msg}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="table-responsive shadow-sm rounded">
        <table class="table table-hover align-middle">
            <thead class="table-primary">
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th class="text-center">Status</th>
                <th>Reason (if deactivated)</th>
                <th class="text-center">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td class="fw-semibold">${user.firstName} ${user.lastName}</td>
                    <td>${user.email}</td>
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${user.active}">
                                <span class="badge bg-success">Active</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-danger">Inactive</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${user.deactivationReason != null ? user.deactivationReason : '-'}" /></td>
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${user.active}">
                                <!-- Deactivate Button triggers modal -->
                                <button 
                                    type="button"
                                    class="btn btn-warning btn-sm"
                                    data-bs-toggle="modal"
                                    data-bs-target="#reasonModal"
                                    data-userid="${user.userId}"
                                    data-username="${user.firstName} ${user.lastName}">
                                    Deactivate
                                </button>
                            </c:when>
                            <c:otherwise>
                                <!-- Activate via form -->
                                <form method="post" action="ManageUsersServlet" class="d-inline">
                                    <input type="hidden" name="userId" value="${user.userId}" />
                                    <input type="hidden" name="action" value="activate" />
                                    <button type="submit" class="btn btn-success btn-sm">Activate</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="text-center mt-4">
        <a href="admin-dashboard.jsp" class="btn btn-outline-primary btn-lg">
            ← Back to Dashboard
        </a>
    </div>
</div>

<!-- Deactivation Reason Modal -->
<div class="modal fade" id="reasonModal" tabindex="-1" aria-labelledby="reasonModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form method="post" action="ManageUsersServlet" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="reasonModalLabel">Deactivate User</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="userId" id="modalUserId" />
                <input type="hidden" name="action" value="deactivate" />
                <div class="mb-3">
                    <label for="reason" class="form-label fw-semibold">Reason for Deactivation</label>
                    <textarea class="form-control" id="reason" name="reason" rows="3" placeholder="Provide reason here..." required></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-warning fw-semibold">Deactivate</button>
            </div>
        </form>
    </div>
</div>

<script>
    
    var reasonModal = document.getElementById('reasonModal');
    reasonModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var userId = button.getAttribute('data-userid');
        var username = button.getAttribute('data-username');

        var modalTitle = reasonModal.querySelector('.modal-title');
        var modalUserIdInput = reasonModal.querySelector('#modalUserId');
        var textarea = reasonModal.querySelector('#reason');

        modalTitle.textContent = 'Deactivate User: ' + username;
        modalUserIdInput.value = userId;
        textarea.value = ''; 
    });
</script>

</body>
</html>
