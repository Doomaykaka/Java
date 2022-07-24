package main.java.medianotes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.medianotes.auth.Authentication;

@RestController
public class mainController {
	//методы
	
	@RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.GET)
	public String CreateMain(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String cookieName = "password";
		if (cookies != null) {
			for (javax.servlet.http.Cookie c : cookies) {
				if (cookieName.equals(c.getName())) {
					c.setValue("");
					response.addCookie(c);
				}
			}
		}
		String html;
		html = "<h2>Hello to medianotes app</h2>";
		html += "<h2>Input login and password:</h2>";
		html += "<form action=\"\" method=\"post\">";
		html += "<p>Login </p><input type=\"text\" name=\"log\"></input>";
		html += "<p>Password </p><input type=\"text\" name=\"pas\"></input>";
		html += "<button class=\"form_auth_button\" type=\"submit\" name=\"form_auth_submit\">Login</button>";
		html += "</form>";
		return html;
	}

	@RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.POST)
	public void ProcessingMain(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uname = request.getParameter("log");
		String pword = request.getParameter("pas");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Authentication auth = new Authentication();
		boolean login = auth.findAccount(uname + " " + pword);

		if (login) {
			out.println("<p>Hello " + uname + "</p>");
			response.addCookie(new Cookie("user", uname));
			response.addCookie(new Cookie("password", pword));
			response.sendRedirect(request.getContextPath() + "/menu");
		} else {
			out.println("<p>Bad login</p>");
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
