package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//System.out.println("Index Servlet: doGet");
		
		
		String function = req.getParameter("function");

        if ("Add Numbers".equals(function)) {
            req.getRequestDispatcher("/_view/addNumbers.jsp").forward(req, resp);
        } else if ("Multiply Numbers".equals(function)) {
            req.getRequestDispatcher("/_view/multiplyNumbers.jsp").forward(req, resp);
        } else if ("Guessing Game".equals(function)) {
            req.getRequestDispatcher("/_view/guessingGame.jsp").forward(req, resp);
        } else {
        	req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
        }
	}
}
