<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.aurionpro.model.Transaction"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Passbook</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">

    <style>
        .dataTables_filter, .dataTables_length {
            margin-bottom: 15px;
        }
        .filter-buttons {
            display: flex;
            gap: 0.5rem;
            flex-wrap: wrap;
        }
        .truncate {
            max-width: 300px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-12">

                <div class="card shadow-lg rounded-4">
                    <div class="card-header text-center bg-primary text-white rounded-top-4">
                        <h4>Your Passbook</h4>
                    </div>
                    <div class="card-body">

                        <!-- Filter Form -->
                        <form action="PassbookServlet" method="get" class="row g-3 mb-4 align-items-end">
                            <div class="col-md-4">
                                <label for="fromDate" class="form-label">From Date</label>
                                <input type="date" id="fromDate" name="fromDate" class="form-control" value="${param.fromDate}">
                            </div>
                            <div class="col-md-4">
                                <label for="toDate" class="form-label">To Date</label>
                                <input type="date" id="toDate" name="toDate" class="form-control" value="${param.toDate}">
                            </div>
                            <div class="col-md-4 filter-buttons">
                                <button type="submit" class="btn btn-primary">Filter</button>
                                <a href="PassbookServlet" class="btn btn-secondary">Reset</a>
                                <a href="CustomerDashboardServlet" class="btn btn-outline-primary fw-bold">Back to Dashboard</a>
                            </div>
                        </form>

                        <!-- Transaction Table -->
                        <div class="table-responsive">
                            <table id="transactionTable" class="table table-bordered table-striped table-hover">
                                <thead class="table-dark">
                                    <tr>
                                        <th scope="col">Transaction ID</th>
                                        <th scope="col">Amount</th>
                                        <th scope="col">Type</th>
                                        <th scope="col">Description</th>
                                        <th scope="col">Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${not empty transactions}">
                                            <c:forEach var="txn" items="${transactions}">
                                                <tr>
                                                    <td>${txn.transactionId}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${txn.transactionType == 'DEBIT'}">
                                                                <span class="text-danger">-₹<fmt:formatNumber value="${txn.amount}" pattern="#,##0.00"/></span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="text-success">+₹<fmt:formatNumber value="${txn.amount}" pattern="#,##0.00"/></span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${txn.transactionType == 'DEBIT'}">
                                                                <span class="badge bg-danger">Debit</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-success">Credit</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td title="${txn.description}" class="truncate">${txn.description}</td>
                                                    <td><fmt:formatDate value="${txn.transactionDate}" pattern="dd-MM-yyyy HH:mm:ss"/></td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="5" class="text-center">No transactions found.</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    </div>

    <!-- jQuery and DataTables JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>

    <!-- DataTables Initialization -->
    <script>
        $(document).ready(function() {
            $('#transactionTable').DataTable({
                "pageLength": 4,
                "lengthMenu": [5, 10, 25, 50],
                "order": [[4, "desc"]] // Sort by Date column descending
            });
        });
    </script>
</body>
</html>
