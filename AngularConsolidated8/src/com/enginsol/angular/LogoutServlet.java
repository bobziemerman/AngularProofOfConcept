package com.enginsol.angular;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     * 
     * Default constructor
     */
    public LogoutServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * Logs the user out of their session and force them to reauthenticate before the service can be accessed again
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//the following four lines are alternative logout methods and may be needed in different authentication circumstances
		//response.setHeader("Cache-Control", "no-cache, no-store");
        //response.setHeader("Pragma", "no-cache");
        //response.setHeader("Expires", new java.util.Date().toString());
        //request.getSession().invalidate();
		
        request.logout();
        response.sendRedirect(request.getContextPath());
	}

}
