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
import main.java.medianotes.repository.FolderRepository;
import main.java.medianotes.repository.NoteRepository;
import main.java.medianotes.repository.impl.FolderRepositoryImpl;
import main.java.medianotes.repository.impl.NoteRepositoryImpl;

public class menuServlet extends HttpServlet{
	//поля сервлета
	
	private static final NoteRepository noteRepository = new NoteRepositoryImpl(); //создаём объект интерфейса для хранения запсок
	private static final FolderRepository folderRepository = new FolderRepositoryImpl(); //создаём объект интерфейса для хранения запсок
	
	private String name = "";
	
	//методы сервлета
	
	private boolean checkLogin(HttpServletRequest req) throws IOException {
		boolean status = false;
		
		Cookie[] cookies = req.getCookies();
        String cookieName1 = "user";
        String cookieName2 = "password";
        Cookie cookieLogin = null;
        Cookie cookiepassword = null;
        if(cookies !=null) {
            for(Cookie c: cookies) {
                if(cookieName1.equals(c.getName())) {
                	cookieLogin = c;
                }
                if(cookieName2.equals(c.getName())) {
                	cookiepassword = c;
                }
            }
        }
        
        Authentication auth = new Authentication();
        status=auth.findAccount(cookieLogin.getValue()+" "+cookiepassword.getValue());
        
        name=cookieLogin.getValue();
        
		return status;
	}
	
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException , IOException{
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
			if(checkLogin(req)) {
				writer.println("<p>Hello "+name+"</p>");
				writer.println("<a href=\""+req.getContextPath() + "/\">LogOut</a>");
				writer.println("<h2>Folders</h2>");
				writer.println("<a href=\""+req.getContextPath() + "/createFolder\">Create folder</a>");
				
				var folders = folderRepository.getFolders(); //получаем список всех папок
				
				for(var folder : folders) { //выводим их содержимое
					writer.printf("<p>Path: %S </p><a href=\""+req.getContextPath() + "/deleteFolder?id="+folder.getName()+"\">Delete folder</a>\n",folderRepository.GetPath(folder.getName(), folder.getParentFolder()));
				}
				
				writer.println("<h2>Notes</h2>");
				writer.println("<a href=\""+req.getContextPath() + "/createNote\">Create note</a>");
				
				var notes = noteRepository.getAllNotes(); //получаем список всех записок
				
				for(var note : notes) { //выводим их содержимое
					writer.printf("<p>Path: %S , Name: %s , Text: %s , Author: %s , CreationDate: %s </p><a href=\""+req.getContextPath() + "/deleteNote?id="+note.getName()+"\">Delete note</a>\n",note.getParentFolder().getName(),note.getName(),note.getText(),note.getAuthor(),note.getCreationDate().toString());
				}
			}else {		
				writer.println("<p>Bad cookie</p>");
			}
		}finally {
			
			writer.close();
		}
	}
}
