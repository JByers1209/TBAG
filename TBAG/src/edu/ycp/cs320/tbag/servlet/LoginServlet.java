package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Retrieve username and password 
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        //authentication//////
        // Authenticate user (e.g., check credentials against a database)
        boolean isAuthenticated = authenticate(username, password);

        if (isAuthenticated) {
            // Create a session and store user information
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            // Redirect user to the home page or some other protected resource
            resp.sendRedirect("/home");
        } else {
            // Invalid credentials, redirect back to login page with an error message
            resp.sendRedirect("/login?error=1");
        }
    
		//database connection??? 
        //will need changed----------------------------------------------------------
//        String url = "jdbc:mysql://localhost:3306/your_database_name";
//        String dbUsername = "your_database_username";
//        String dbPassword = "your_database_password";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
// will need changed -----------------------------------------------------------------
        
        try { 
        	//connect to db 
        	
        	//
        	
        	// SQl Query to retrieve user info 
        	String sql = " SELECT password From users WHERE username = ? ";
        	stmt = conn.prepareStatement(sql);
        	stmt.setString(1, username); 
        	rs = stmt.executeQuery();
        	
        	 // username found, check pass 
            if (rs.next()) {
            	String storedPass = rs.getString("password");
            	
            	if(password.equals(storedPass) ) {
            		//pass matches redirect to index 
            		resp.sendRedirect(req.getContextPath() + "/index.jsp");
            	}
            } 
            else {
                // If not valid, forward back to the login page with an error message
                req.setAttribute("errorMessage", "Invalid username or password"); 
            }
          
        }
        catch( SQLException e){
        	e.printStackTrace();    	
			resp.sendRedirect("login-error.html");      	
    }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
        }
        }
}

    private boolean authenticate(String username, String password) {
		// TODO Auto-generated method stub
    	
    	
		return false;
	}
 
//
//	// Example method to check if username and password are valid (replace with your logic)
//    private boolean isValidLogin(String username, String password) {
//        // Example validation logic (replace with actual validation logic)
//        // This is just a simple example
//        return "admin".equals(username) && "password".equals(password);
//    }
}
