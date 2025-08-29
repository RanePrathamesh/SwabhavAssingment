package com.aurionpro.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.User;
import com.aurionpro.service.UserService;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private UserService userService ;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
        userService = new UserService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  HttpSession session = request.getSession(false);
	        User user = (User) session.getAttribute("user");

	        if (user == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        request.setAttribute("user", user);
	        request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
	    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession(false);
	        User user = (User) session.getAttribute("user");

	        if (user == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        // Get updated values
	        user.setFirstName(request.getParameter("firstName"));
	        user.setLastName(request.getParameter("lastName"));
	        user.setEmail(request.getParameter("email"));
	        user.setMobileNumber(request.getParameter("mobileNumber"));
	        user.setAge(Integer.parseInt(request.getParameter("age")));
	        user.setGender(request.getParameter("gender"));
	        user.setAddress(request.getParameter("address"));

	        boolean success = userService.updateUser(user);

	        if (success) {
	            session.setAttribute("user", user); // update session user
	            request.setAttribute("msg", "✅ Profile updated successfully!");
	        } else {
	            request.setAttribute("msg", "❌ Failed to update profile.");
	        }

	        request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
	    
	}

}
