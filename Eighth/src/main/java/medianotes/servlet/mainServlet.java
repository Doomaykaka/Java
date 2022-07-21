package main.java.medianotes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import main.java.medianotes.auth.Authentication;
import main.java.medianotes.repository.NoteRepository;
import main.java.medianotes.repository.impl.NoteRepositoryImpl;

public class mainServlet extends HttpServlet{
	//методы сервлета
	
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException , IOException{
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
			Cookie[] cookies = req.getCookies();
			String cookieName = "password";
			if(cookies !=null) {
	            for(Cookie c: cookies) {
	                if(cookieName.equals(c.getName())) {
	                	c.setValue("");
	                	resp.addCookie(c);
	                }
	            }
	        }
			
			writer.println("<h2>Hello to medianotes app</h2>");
			writer.println("<h2>Input login and password:</h2>");
			writer.println("<form action=\"\" method=\"post\">");
			writer.println("<p>Login </p><input type=\"text\" name=\"log\"></input>");
			writer.println("<p>Password </p><input type=\"text\" name=\"pas\"></input>");
			writer.println("<button class=\"form_auth_button\" type=\"submit\" name=\"form_auth_submit\">Login</button>");
			writer.println("</form>");
		}finally {
			writer.close();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String uname=request.getParameter("log");  
	    String pword=request.getParameter("pas");          
	    response.setContentType("text/html");  
	    PrintWriter out = response.getWriter(); 
	    
	    try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Authentication auth = new Authentication();
	    boolean login = auth.findAccount(uname+" "+pword);
	               
	    if(login) {  
	    	out.println("<p>Hello "+uname+"</p>");
	    	response.addCookie(new Cookie("user", uname));
	    	response.addCookie(new Cookie("password", pword));
	    	response.sendRedirect(request.getContextPath() + "/menu");
	    }
	    else{           
	    	out.println("<p>Bad login</p>");
	    	response.sendRedirect(request.getContextPath() + "/");
	    }
	    
	}
}
