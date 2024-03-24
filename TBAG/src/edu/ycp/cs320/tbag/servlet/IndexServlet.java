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
		
		String function = req.getParameter("function");

        if ("Play".equals(function)) {
            req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
        } 
        else if("Log In!".equals(function)) {
        	req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
        }
        else {
        	req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
        }
       
	}
}

