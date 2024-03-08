package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.tbag.controller.NumbersController;
import edu.ycp.cs320.tbag.model.Numbers;

public class AddNumbersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("AddNumbers Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/addNumbers.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("AddNumbers Servlet: doPost");
		// holds the error message text, if there is any
		String errorMessage = null;
		
		// decode POSTed form parameters and dispatch to controller
		Numbers model = new Numbers(null, null, null, null);
		
		try {
			
			model.setFirst(getDoubleFromParameter(req.getParameter("first")));
			model.setSecond(getDoubleFromParameter(req.getParameter("second")));
			model.setThird(getDoubleFromParameter(req.getParameter("third")));

			// check for errors in the form data before using is in a calculation
			if (model.getFirst() == null || model.getSecond() == null || model.getThird() == null) {
				errorMessage = "Please specify three numbers";
			}
			// otherwise, data is good, do the calculation
			// must create the controller each time, since it doesn't persist between POSTs
			// the view does not alter data, only controller methods should be used for that
			// thus, always call a controller method to operate on the data
			else {
				NumbersController controller = new NumbersController();
				model.setResult(controller.add(model.getFirst(), model.getSecond(), model.getThird()));
			}
		} catch (NumberFormatException e) {
			errorMessage = "Invalid double";
		}
	
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("model", model);
		req.setAttribute("errorMessage", errorMessage);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/addNumbers.jsp").forward(req, resp);
	}

	// gets double from the request with attribute named s
	private Double getDoubleFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Double.parseDouble(s);
		}
	}
}
