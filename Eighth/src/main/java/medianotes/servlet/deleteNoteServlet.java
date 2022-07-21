package main.java.medianotes.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.medianotes.auth.Authentication;
import main.java.medianotes.model.Folder;
import main.java.medianotes.repository.FolderRepository;
import main.java.medianotes.repository.NoteRepository;
import main.java.medianotes.repository.impl.FolderRepositoryImpl;
import main.java.medianotes.repository.impl.NoteRepositoryImpl;

public class deleteNoteServlet extends HttpServlet {
	// поля сервлета

	private static final NoteRepository noteRepository = new NoteRepositoryImpl(); // создаём объект интерфейса для хранения запсок
	private static final FolderRepository folderRepository = new FolderRepositoryImpl(); // создаём объект интерфейса для хранения запсок

	private String name = "";
	private boolean statusGlob = false;

	// методы сервлета

	private boolean checkLogin(HttpServletRequest req) throws IOException {
		boolean status = false;

		Cookie[] cookies = req.getCookies();
		String cookieName1 = "user";
		String cookieName2 = "password";
		Cookie cookieLogin = null;
		Cookie cookiepassword = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (cookieName1.equals(c.getName())) {
					cookieLogin = c;
				}
				if (cookieName2.equals(c.getName())) {
					cookiepassword = c;
				}
			}
		}

		Authentication auth = new Authentication();
		status = auth.findAccount(cookieLogin.getValue() + " " + cookiepassword.getValue());

		name = cookieLogin.getValue();
		statusGlob = status;

		return status;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
			if (checkLogin(req)) {
				String name = req.getParameter("id");
				writer.println("<h2>Delete folder?</h2>");
				writer.println("<form action=\"\" method=\"post\">");
				writer.println("<p>Folder </p><input type=\"text\" name=\"nname\" value=\"" + name + "\"></input>");
				writer.println(
						"<button class=\"form_auth_button\" type=\"submit\" name=\"form_auth_submit\">Delete</button>");
			} else {
				writer.println("<p>Bad cookie</p>");
				resp.sendRedirect(req.getContextPath() + "/");
			}
		} finally {

			writer.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("nname");
		PrintWriter writer = response.getWriter();
		try {
			if (statusGlob) {
				noteRepository.remove(name); // удаляем записку по полученному имени
				writer.println("Note deleted"); // выводим информирующее сообщение
				response.sendRedirect(request.getContextPath() + "/menu");
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
