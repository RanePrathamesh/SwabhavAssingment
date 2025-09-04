package com.aurionpro.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.AdminViewUserDto;
import com.aurionpro.model.User;
import com.aurionpro.service.UserService;

/**
 * Servlet implementation class ManageUsersServlet
 */
@WebServlet("/ManageUsersServlet")
public class ManageUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
        userService = new UserService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<AdminViewUserDto> users = userService.getAllNonAdminUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("manage-users.jsp").forward(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action"); 
        String reason = request.getParameter("reason");
        String userIdStr = request.getParameter("userId");

        if (action != null && userIdStr != null && !userIdStr.trim().isEmpty()) {
            int userId = Integer.parseInt(userIdStr);  

            boolean isActive = action.equalsIgnoreCase("activate");

            boolean updated = userService.updateUserActiveStatus(userId, isActive, reason);

            if (updated) {
                request.setAttribute("msg", "User " + (isActive ? "activated" : "deactivated") + " successfully.");
            } else {
                request.setAttribute("msg", "Failed to update user status.");
            }

        } else {
            request.setAttribute("msg", " Invalid request. User ID or action missing.");
        }

        doGet(request, response); 
    }

}
