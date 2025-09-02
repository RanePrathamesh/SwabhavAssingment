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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-9">
                <div class="card shadow-lg rounded-4">
                    <div class="card-header text-center bg-primary text-white rounded-top-4">
                        <h4>Your Passbook</h4>
                    </div>
                    <div class="card-body">

                        <%
                        List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
                        %>

                        <table class="table table-bordered table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>Transaction ID</th>
                                    <th>Amount</th>
                                    <th>Type</th>
                                    <th>To Account</th>
                                    <th>Date</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty transactions}">
                                        <c:forEach var="txn" items="${transactions}">
                                            <tr>
                                                <td>${txn.txnId}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${txn.txnType == 'DEBIT'}">
                                                            <span class="text-danger">-₹${txn.amount}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-success">+₹${txn.amount}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${txn.txnType}</td>
                                                <td>${txn.toAccountNumber}</td>
                                                <td><fmt:formatDate value="${txn.txnDate}" pattern="dd-MM-yyyy HH:mm:ss"/></td>
                                                <td>${txn.status}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="6" class="text-center">No transactions found.</td>
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
</body>
</html>
