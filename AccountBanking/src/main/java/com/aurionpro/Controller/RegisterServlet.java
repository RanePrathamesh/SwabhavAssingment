package com.aurionpro.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aurionpro.model.User;
import com.aurionpro.service.UserService;
import com.aurionpro.utils.FormatChecker;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
        userService = new UserService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String firstName     = request.getParameter("firstName");
	        String lastName      = request.getParameter("lastName");
	        String email         = request.getParameter("email");
	        String mobile        = request.getParameter("mobileNumber");
	        String ageParam      = request.getParameter("age");
	        String gender        = request.getParameter("gender");
	        String address       = request.getParameter("address");

	        
	        if (!FormatChecker.isValidString(firstName) || !FormatChecker.isValidString(lastName)) {
	            request.setAttribute("message", "Invalid name. Only letters and spaces allowed.");
	            request.getRequestDispatcher("register.jsp").forward(request, response);
	            return;
	        }

	        if (!FormatChecker.isValidEmail(email)) {
	            request.setAttribute("message", "Invalid email format.");
	            request.getRequestDispatcher("register.jsp").forward(request, response);
	            return;
	        }

	        if (!FormatChecker.isMobileNumberValid(mobile)) {
	            request.setAttribute("message", "Invalid mobile number. Must be 10 digits starting with 6–9.");
	            request.getRequestDispatcher("register.jsp").forward(request, response);
	            return;
	        }

	        int age;
	        try {
	            age = Integer.parseInt(ageParam);
	            if (age < 18) throw new NumberFormatException();
	        } catch (NumberFormatException ex) {
	            request.setAttribute("message", "Age must be a number atleast 18.");
	            request.getRequestDispatcher("register.jsp").forward(request, response);
	            return;
	        }

	        if (!FormatChecker.isValidString(address)) {
	            request.setAttribute("message", "Invalid address. Only letters and spaces allowed.");
	            request.getRequestDispatcher("register.jsp").forward(request, response);
	            return;
	        }

	        
	        User user = new User(firstName, lastName, email, mobile, age, gender, address);

	        // Service call
	        String resultMsg = userService.registerUser(user);

	        // ======== RESPONSE ========
	        request.setAttribute("message", resultMsg);
	        request.getRequestDispatcher("Register.jsp").forward(request, response);
	    
	}

}
