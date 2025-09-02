package com.aurionpro.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.aurionpro.model.Transaction;
import com.aurionpro.service.PaymentService;

@WebServlet("/PassbookServlet")
public class PassbookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PaymentService paymentService;

    public PassbookServlet() {
        super();
        this.paymentService = new PaymentService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int accountId = (int) session.getAttribute("account");

        // Get date filters from request
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");

        List<Transaction> transactions;

        if (fromDateStr != null && toDateStr != null && !fromDateStr.isEmpty() && !toDateStr.isEmpty()) {
            transactions = paymentService.getPassbookHistory(accountId, fromDateStr, toDateStr);
        } else {
            transactions = paymentService.getPassbookHistory(accountId);
        }

        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("passbook.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
