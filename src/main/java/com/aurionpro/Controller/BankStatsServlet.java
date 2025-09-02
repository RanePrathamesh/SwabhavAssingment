package com.aurionpro.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.User;
import com.aurionpro.service.AccountService;

/**
 * Servlet implementation class BankStatsServlet
 */
@WebServlet("/BankStatsServlet")
public class BankStatsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private AccountService accountService = new AccountService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BankStatsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  HttpSession session = request.getSession(false);
    	 if (session == null) {
    	        response.sendRedirect("Login.jsp");
    	        return;
    	    }

    	    User user = (User) session.getAttribute("user");
    	    if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
    	        response.sendRedirect("Login.jsp");
    	        return;
    	    }
        double totalBalance = accountService.getTotalBankBalance();
        request.setAttribute("totalBalance", totalBalance);

        request.getRequestDispatcher("bank-stats.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
