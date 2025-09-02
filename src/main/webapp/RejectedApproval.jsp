<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.aurionpro.model.PendingAccountDto" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Rejected Accounts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-dark bg-primary shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">MyBank Admin Panel</a>
            <div class="d-flex align-items-center gap-3">
                <a href="LogoutServlet" class="btn btn-outline-light btn-sm">
                    <i class="bi bi-box-arrow-right"></i> Logout
                </a>
            </div>
        </div>
    </nav>

    <!-- Main Container -->
    <main class="container mt-4">
        <h2 class="text-center fw-bold text-primary">Rejected Accounts</h2>

        <table class="table table-striped mt-4">
            <thead>
                <tr>
                    <th>Account ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Rejection Reason</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<PendingAccountDto> rejectedAccounts = (List<PendingAccountDto>) request.getAttribute("rejectedAccounts");
                    if (rejectedAccounts != null && !rejectedAccounts.isEmpty()) {
                        for (PendingAccountDto account : rejectedAccounts) {
                %>
                    <tr>
                        <td><%= account.getAccountId() %></td>
                        <td><%= account.getFirstName() + " " + account.getLastName() %></td>
                        <td><%= account.getEmail() %></td>
                        <td><%= account.getRejectionReason() %></td>
                        <td><%= account.getCreatedAt() %></td>
                        <td>
                            <form action="ApproveAccountServlet" method="POST" style="display:inline;">
                                <input type="hidden" name="accountId" value="<%= account.getAccountId() %>"/>
                                <button type="submit" class="btn btn-success btn-sm">Approve</button>
                            </form>
                        </td>
                    </tr>
                <% 
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="6" class="text-center">No rejected accounts found.</td>
                    </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
         <div class="text-center mb-3">
                            <a href="admin-dashboard.jsp" class="btn btn-outline-primary"> Back to Dashboard</a>
                        </div>

    </main>

    <!-- Footer -->
    <footer class="footer bg-dark text-white text-center p-3">
        &copy; 2025 MyBank Admin Dashboard
    </footer>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
