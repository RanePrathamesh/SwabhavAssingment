package com.aurionpro.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.Beneficiary;
import com.aurionpro.model.User;
import com.aurionpro.service.BeneficiaryService;

/**
 * Servlet implementation class BeneficiaryServlet
 */
@WebServlet("/BeneficiaryServlet")
public class BeneficiaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private BeneficiaryService beneficiaryService;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeneficiaryServlet() {
        super();
        // TODO Auto-generated constructor stub
        beneficiaryService = new BeneficiaryService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(false);
	        User user = (session != null) ? (User) session.getAttribute("user") : null;

	        if (user == null) {
	            response.sendRedirect("Login.jsp");
	            return;
	        }

	        int userId = user.getUserId();
	        List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesByUserId(userId);
	        request.setAttribute("beneficiaries", beneficiaries);
	        request.getRequestDispatcher("beneficiaries.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
        	response.sendRedirect("Login.jsp");
            return;
        }

        int userId = user.getUserId();

        switch (action == null ? "" : action.toLowerCase()) {
            case "add":
                handleAdd(request, response, userId);
                break;
            case "update":
                handleUpdate(request, response, userId);
                break;
            case "delete":
                handleDelete(request, response, userId);
                break;
            default:
            	response.sendRedirect("BeneficiaryServlet?error=InvalidAction");
                break;
        }
	}
	
	
	
	private void handleAdd(HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException {
        Beneficiary beneficiary = buildBeneficiaryFromRequest(req, userId);
        boolean added = beneficiaryService.addBeneficiary(beneficiary);
        if (added) {
            resp.sendRedirect("BeneficiaryServlet?success=Added");
        } else {
            resp.sendRedirect("BeneficiaryServlet?error=AddFailed");
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException {
        String idStr = req.getParameter("beneficiaryId");
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect("BeneficiaryServlet?error=InvalidId");
            return;
        }

        Beneficiary beneficiary = buildBeneficiaryFromRequest(req, userId);
        beneficiary.setBeneficiaryId(Integer.parseInt(idStr));

        boolean updated = beneficiaryService.updateBeneficiary(beneficiary);
        if (updated) {
            resp.sendRedirect("BeneficiaryServlet?success=Updated");
        } else {
            resp.sendRedirect("BeneficiaryServlet?error=UpdateFailed");
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp, int userId) throws IOException {
        String idStr = req.getParameter("beneficiaryId");
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect("BeneficiaryServlet?error=InvalidId");
            return;
        }

        int beneficiaryId = Integer.parseInt(idStr);
        boolean deleted = beneficiaryService.deleteBeneficiary(beneficiaryId, userId);
        if (deleted) {
            resp.sendRedirect("BeneficiaryServlet?success=Deleted");
        } else {
            resp.sendRedirect("BeneficiaryServlet?error=DeleteFailed");
        }
    }

    private Beneficiary buildBeneficiaryFromRequest(HttpServletRequest req, int userId) {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setUserId(userId);
        beneficiary.setBeneficiaryName(req.getParameter("beneficiaryName"));
        beneficiary.setAccountNumber(req.getParameter("accountNumber"));
        beneficiary.setBankName(req.getParameter("bankName"));
        beneficiary.setIfscCode(req.getParameter("ifscCode"));
        beneficiary.setNickname(req.getParameter("nickname"));
        return beneficiary;
    }

}
