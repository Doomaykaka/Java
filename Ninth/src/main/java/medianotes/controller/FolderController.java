package main.java.medianotes.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.medianotes.auth.Authentication;
import main.java.medianotes.model.Folder;
import main.java.medianotes.repository.FolderRepository;
import main.java.medianotes.repository.NoteRepository;
import main.java.medianotes.repository.impl.FolderRepositoryImpl;
import main.java.medianotes.repository.impl.NoteRepositoryImpl;

@RestController
public class FolderController {
	//поля
	
	private static final NoteRepository noteRepository = new NoteRepositoryImpl(); // создаём объект интерфейса для
																					// хранения запсок
	private static final FolderRepository folderRepository = new FolderRepositoryImpl(); // создаём объект интерфейса
																							// для хранения запсок
	private boolean statusGlob = false;
	
	//методы

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

	@RequestMapping(value = "/createFolder", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.GET)
	protected void CreateFolderGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
			if (checkLogin(req)) {
				writer.println("<h2>Folder</h2>");

				writer.println("<form action=\"\" method=\"post\">");
				writer.println("<p>Folder name </p><input type=\"text\" name=\"fol\"></input>");
				writer.println("<p>Parent folder </p><input type=\"text\" name=\"pfol\"></input>");
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

	@RequestMapping(value = "/createFolder", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.POST)
	protected void CreateFolderPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String folname = request.getParameter("fol");
		String pfolname = request.getParameter("pfol");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			if (statusGlob) {
				boolean res2 = true; // ищем такую папку и в случае отсутствия продолжаем работу
				for (var fal : folderRepository.getFolders()) {
					if ((fal.getName().equals(folname)) && (fal.getParentFolder().getName().equals(pfolname))) {
						res2 = false;
					}
				}

				if (!res2) {
					out.println("This folder has already been created"); // выводим информирующее сообщение
					response.sendRedirect(request.getContextPath() + "/menu");
				}

				Folder par; // искомая папка
				par = folderRepository.FindFolder(pfolname);
				if (par == null) {
					System.out.println("Bad parent folder"); // выводим информирующее сообщение
					response.sendRedirect(request.getContextPath() + "/menu");
				} else {
					if ((folname.equals("")) || (par == null)) {
						throw new Exception("Params empty");
					}
					folderRepository.CreateFolder(folname, par);
					System.out.println("Folder created"); // выводим информирующее сообщение
					response.sendRedirect(request.getContextPath() + "/menu");
				}
			} else {
				out.println("<p>Bad login</p>");
				response.sendRedirect(request.getContextPath() + "/");
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}

	@RequestMapping(value = "/deleteFolder", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.GET)
	protected void DeleteFolderGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
			if (checkLogin(req)) {
				String name = req.getParameter("id");
				writer.println("<h2>Delete folder?</h2>");
				writer.println("<form action=\"\" method=\"post\">");
				writer.println("<p>Folder </p><input type=\"text\" name=\"fol\" value=\"" + name + "\"></input>");
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

	@RequestMapping(value = "/deleteFolder", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.POST)
	protected void DeleteFolderPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String folname = request.getParameter("fol");
		PrintWriter writer = response.getWriter();
		try {
			if (statusGlob) {
				Folder par; // искомая папка
				par = folderRepository.FindFolder(folname);
				if (par == null) {
					writer.println("Folder not deleted"); // выводим информирующее сообщение
				} else {
					folderRepository.RemoveFolder(folname, par.getParentFolder().getName());
					writer.println("Folder deleted"); // выводим информирующее сообщение
					response.sendRedirect(request.getContextPath() + "/menu");
				}

			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
