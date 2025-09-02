package com.aurionpro.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aurionpro.model.AllTransactionDto;
import com.aurionpro.model.Transaction;
import com.aurionpro.model.User;
import com.aurionpro.service.PaymentService;

/**
 * Servlet implementation class AllTransactionsServlet
 */
@WebServlet("/AllTransactionsServlet")
public class AllTransactionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private PaymentService paymentService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllTransactionsServlet() {
        super();
        paymentService = new PaymentService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optional: check for admin session
    	
        Integer accountId = (Integer) request.getSession().getAttribute("account");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("Login.jsp");
            return;
        }

        List<AllTransactionDto> allTransactions = paymentService.getAllTransactions(); 
        	
        
        request.setAttribute("transactions", allTransactions);
        request.getRequestDispatcher("allTransactions.jsp").forward(request, response);
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
