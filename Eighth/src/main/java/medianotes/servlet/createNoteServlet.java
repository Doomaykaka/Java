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
import main.java.medianotes.model.Note;
import main.java.medianotes.repository.FolderRepository;
import main.java.medianotes.repository.NoteRepository;
import main.java.medianotes.repository.impl.FolderRepositoryImpl;
import main.java.medianotes.repository.impl.NoteRepositoryImpl;

public class createNoteServlet extends HttpServlet {
	// поля сервлета

	private static final NoteRepository noteRepository = new NoteRepositoryImpl(); // создаём объект интерфейса для хранения запсок
	private static final FolderRepository folderRepository = new FolderRepositoryImpl(); // создаём объект интерфейса для хранения запсок

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

		statusGlob = status;

		return status;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
			if (checkLogin(req)) {
				writer.println("<h2>Note</h2>");

				writer.println("<form action=\"\" method=\"post\">");
				writer.println("<p>Note name </p><input type=\"text\" name=\"nname\"></input>");
				writer.println("<p>Note author </p><input type=\"text\" name=\"nauthor\"></input>");
				writer.println("<p>Note folder </p><input type=\"text\" name=\"nfolder\"></input>");
				writer.println("<p>Note text </p><input type=\"text\" name=\"ntext\"></input>");
				writer.println(
						"<button class=\"form_auth_button\" type=\"submit\" name=\"form_auth_submit\">Create</button>");
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
		String notename = request.getParameter("nname");
		String noteauthor = request.getParameter("nauthor");
		String notefolder = request.getParameter("nfolder");
		String notetext = request.getParameter("ntext");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			if (statusGlob) {
				Folder falderRes = new Folder("", null);

				boolean res = false; // ищем такую папку и в случае удачи копируем объект
				for (var fal : folderRepository.getFolders()) {
					if (fal.getName().equals(notefolder)) {
						res = true;
						falderRes = fal;
					}
				}
				if (!res) { // если не нашли , то прекращаем выполнение
					System.out.println("Folder not exist");
					response.sendRedirect(request.getContextPath() + "/menu");
				}
				
				if((notename.equals(""))||(notetext.equals(""))||(noteauthor.equals(""))||(falderRes==null)) {
					throw new Exception("Params empty");
				}

				Note newNote = new Note(notename, notetext, noteauthor, falderRes); // создаём записку с полученными
																					// полями
				noteRepository.save(newNote);

				out.println("Note created "); // выводим информирующее сообщение
				response.sendRedirect(request.getContextPath() + "/menu");
			} else {
				out.println("<p>Bad cookie</p>");
				response.sendRedirect(request.getContextPath() + "/");
			}
		}catch(Exception e){
			
		} 
	}
}
