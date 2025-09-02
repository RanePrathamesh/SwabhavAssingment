package com.aurionpro.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.aurionpro.model.User;
import com.aurionpro.service.UserService;
import com.aurionpro.utils.FormatChecker;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
@javax.servlet.annotation.MultipartConfig(
    fileSizeThreshold = 1024 * 1024,     // 1MB
    maxFileSize = 5 * 1024 * 1024,       // 5MB
    maxRequestSize = 10 * 1024 * 1024    // 10MB
)
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        userService = new UserService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobileNumber");
        String ageParam = request.getParameter("age");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String aadharNumber = request.getParameter("aadharNumber");

        // === Validate Input ===
        if (!FormatChecker.isValidString(firstName) || !FormatChecker.isValidString(lastName)) {
            request.setAttribute("message", "Invalid name. Only letters and spaces allowed.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        if (!FormatChecker.isValidEmail(email)) {
            request.setAttribute("message", "Invalid email format.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        if (!FormatChecker.isMobileNumberValid(mobile)) {
            request.setAttribute("message", "Invalid mobile number. Must be 10 digits starting with 6–9.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageParam);
            if (age < 18) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Age must be a number and at least 18.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        if (!FormatChecker.isValidString(address)) {
            request.setAttribute("message", "Invalid address. Only letters and spaces allowed.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // === Aadhar Validation (Simple) ===
        if (aadharNumber == null || !aadharNumber.matches("\\d{12}")) {
            request.setAttribute("message", "Invalid Aadhar number. Must be exactly 12 digits.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        try {
            // === File Upload ===
            Part filePart = request.getPart("aadharFile");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String lowerFileName = fileName.toLowerCase();

            if (!(lowerFileName.endsWith(".jpg") || lowerFileName.endsWith(".jpeg") || lowerFileName.endsWith(".png"))) {
                request.setAttribute("message", "Invalid file type. Only JPG and PNG formats are allowed.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }


            if (fileName == null || fileName.isEmpty()) {
                request.setAttribute("message", "Aadhar file is required.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }

            // Check file size before saving it
            long fileSize = filePart.getSize();
            if (fileSize > 5 * 1024 * 1024) {  
                request.setAttribute("message", "The Aadhar file exceeds the 5MB size limit. Please upload a smaller file.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }

            // Save the file to a local path
//            String uploadPath = "C:/Users/prathamesh.rane/Desktop/uploads";
            
            String uploadPath = getServletContext().getRealPath("/uploads");

            
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath); 
            
            User user = new User(firstName, lastName, email, mobile, age, gender, address);
            user.setAadharNumber(aadharNumber);
            user.setAadharFilePath("uploads/" + fileName); 

           
            String resultMsg = userService.registerUser(user);

            request.setAttribute("message", resultMsg);
            request.getRequestDispatcher("Register.jsp").forward(request, response);

        } catch (IllegalStateException e) {
            // Handle file size exceeding the max limit (5MB)
            request.setAttribute("message", "File size exceeds the 5MB limit. Please upload a smaller file.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);

        } catch (Exception e) {
            // Handle other unexpected errors
        	System.out.println(e.getMessage());
            request.setAttribute("message", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }
}
