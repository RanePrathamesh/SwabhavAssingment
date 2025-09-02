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
 * Servlet implementation class ViewBalanceServlet
 */
@WebServlet("/ViewBalanceServlet")
public class ViewBalanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private AccountService accountService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewBalanceServlet() {
        super();
        accountService = new AccountService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
        	response.sendRedirect("Login.jsp");
            return;
        }

        int userId = user.getUserId();

        double balance = accountService.getBalanceByUserId(userId);

        request.setAttribute("balance", balance);
        request.getRequestDispatcher("viewBalance.jsp").forward(request, response);
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
