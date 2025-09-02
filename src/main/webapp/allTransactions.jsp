<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.aurionpro.model.User" %>
<%@ page import="com.aurionpro.model.Transaction" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    User admin = (User) session.getAttribute("user");
    if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect("Login.jsp");
        return;
    }
    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>All Transactions - Admin</title>

    <!-- Bootstrap & DataTables CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css" rel="stylesheet" />

    <style>
        body {
            background-color: #f9fafb;
        }
        h2 {
            font-weight: 700;
        }
        .filter-container {
            margin-bottom: 30px;
        }
        .filter-container .form-control {
            min-height: 40px;
        }
        #adminTransTable_wrapper {
            overflow-x: auto;
        }
        .btn-back {
            margin-top: 20px;
        }
        @media (min-width: 768px) {
            .filter-container .form-control {
                margin-bottom: 0 !important;
            }
        }
    </style>
</head>
<body class="bg-light">

<div class="container mt-5 mb-5">
    <h2 class="text-center mb-4 text-primary">📋 All Transactions</h2>

    <!-- Filters -->
    <form class="row g-3 filter-container justify-content-center align-items-end">

        <div class="col-12 col-md-3">
            <label for="min-date" class="form-label fw-semibold">From Date</label>
            <input type="date" id="min-date" class="form-control" />
        </div>

        <div class="col-12 col-md-3">
            <label for="max-date" class="form-label fw-semibold">To Date</label>
            <input type="date" id="max-date" class="form-control" />
        </div>

        <div class="col-12 col-md-4">
            <label for="search-account" class="form-label fw-semibold">Search Account Number</label>
            <input type="text" id="search-account" class="form-control" placeholder="Enter Account Number" />
        </div>

    </form>

    <!-- Transactions Table -->
    <div class="table-responsive shadow-sm rounded">
        <table id="adminTransTable" class="table table-striped table-bordered table-hover align-middle text-center">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Account Number</th>
                    <th>Type</th>
                    <th>Amount (₹)</th>
                    <th>Description</th>
                    <th>Date & Time</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="txn" items="${transactions}">
                    <tr>
                        <td>${txn.transactionId}</td>
                        <td>${txn.accountNumber}</td>
                        <td>
                            <c:choose>
                                <c:when test="${txn.transactionType == 'DEBIT'}">
                                    <span class="text-danger fw-bold">${txn.transactionType}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-success fw-bold">${txn.transactionType}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <fmt:formatNumber value="${txn.amount}" pattern="#,##0.00" />
                        </td>
                        <td>${txn.description}</td>
                        <td><fmt:formatDate value="${txn.transactionDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="text-center">
        <a href="admin-dashboard.jsp" class="btn btn-outline-primary btn-lg btn-back">
            ← Back to Dashboard
        </a>
    </div>
</div>

<!-- jQuery & DataTables JS -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>

<script>
    $(document).ready(function () {
        // Custom date filter for DataTables
        $.fn.dataTable.ext.search.push(function (settings, data, dataIndex) {
            var min = $('#min-date').val();
            var max = $('#max-date').val();
            var date = data[5]; // Date column

            if (!min && !max) {
                return true;
            }
            if (min && !max) {
                return date >= min;
            }
            if (!min && max) {
                return date <= max;
            }
            if (min && max) {
                return date >= min && date <= max;
            }
            return true;
        });

        var table = $('#adminTransTable').DataTable({
            order: [[5, 'desc']],
            language: {
                search: "Global Search:",
                lengthMenu: "Show _MENU_ entries",
                info: "Showing _START_ to _END_ of _TOTAL_ transactions",
                zeroRecords: "No matching transactions found"
            },
            pageLength: 10,
            lengthMenu: [5, 10, 25, 50]
        });

        // Redraw on date filter change
        $('#min-date, #max-date').on('change', function () {
            table.draw();
        });

        // Search account number filter
        $('#search-account').on('keyup', function () {
            table.column(1).search(this.value).draw();
        });
    });
</script>

</body>
</html>
