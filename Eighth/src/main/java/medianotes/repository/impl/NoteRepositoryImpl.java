package main.java.medianotes.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import main.java.medianotes.model.Folder;
import main.java.medianotes.model.Note;
import main.java.medianotes.repository.NoteRepository;
import main.java.medianotes.repository.impl.FolderRepositoryImpl.SortInt;

//класс хранилища записок
public class NoteRepositoryImpl implements NoteRepository, Serializable {
	// поля класса

	private static final long serialVersionUID = 1L;
	private static Connection db = null; // объект подключения
	private static boolean connection_status = false; // логическая переменная хранящая статус подключения к бд

	// интерфейсы

	// функциональный интерфейс
	interface SortInt {
		// абстрактный метод
		List<Note> getSorted();
	}

	// методы класса

	// метод работы со статическими членами (запускается при старте приложения)
	static {
		String db_type = "postgresql";
		String db_address = "localhost";
		String db_port = "5432";
		String db_name = "Seventh";
		String db_user = "postgres";
		String db_password = "FireStarter11";

		// подключаемся к бд
		try {
			DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance());

			StringBuilder url = new StringBuilder();

			url.append("jdbc:" + db_type + "://").append(db_address + ":").append(db_port + "/").append(db_name + "?")
					.append("user=" + db_user + "&").append("password=" + db_password);

			db = DriverManager.getConnection(url.toString());
			if (db != null) {
				connection_status = true;
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// контруктор класса
	public NoteRepositoryImpl() {
	}

	// сохранение записки
	public Note save(Note note) {
		// добавляем данные о записках
		if (connection_status) {
			try {
				String name = note.getName().toUpperCase();
				String par_fol = note.getParentFolder().getName().toUpperCase();
				String text = note.getText();
				String author = note.getAuthor();
				String cr_date = note.getCreationDate().toString();
				Statement st1 = db.createStatement();
				System.out.println("name - " + name + " pf - " + par_fol + " text - " + text + " author - " + author
						+ " cr date - " + cr_date);
				ResultSet rs1 = st1.executeQuery("SELECT count(*) FROM \"Notes\"");
				if (rs1.next()) {
					int count = rs1.getInt(1);
					System.out.println(Integer.toString(count));
					Statement st = db.createStatement();
					ResultSet rs = st
							.executeQuery("INSERT INTO \"Notes\" (id,parent_folder,name,text,author,creation_date)"
									+ "VALUES (" + Integer.toString(count) + ", '" + par_fol + "', '" + name + "', '"
									+ text + "', '" + author + "', '" + cr_date + "')");
				}
			} catch (SQLException e) {

			} catch (Exception a) {
				a.printStackTrace();
			}

		}

		return note;
	};

	// метод получения списка со всеми записками
	@Override
	public List<Note> getAllNotes() {
		final List<Note> NOTES = new LinkedList<>();
		// запрашиваем данные о записках
		if (connection_status) {
			try {
				Statement st = db.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM \"Notes\"");
				ResultSet vl = rs;
				List<String> allRows = new ArrayList<String>();
				String nm;
				String pf;
				String txt;
				String author;
				String date;
				while (vl.next()) {
					nm = vl.getString("name");
					pf = vl.getString("parent_folder");
					txt = vl.getString("text");
					author = vl.getString("author");
					date = vl.getString("creation_date");
					NOTES.add(new Note(nm, txt, author, FindFolder(pf)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// сортировка списка записок
		SortInt si; // создаём объект функцианального интерфейса
		si = () -> NOTES.stream().sorted(Comparator.comparing(note -> note.getName())).collect(Collectors.toList()); // сортируем
																														// список
																														// записок
																														// при
																														// помощи
																														// лямбда-выражениия

		return si.getSorted();
	}

	// метод удаления записки
	@Override
	public void remove(String name) {
		// удаляем данные о записке
		if (connection_status) {
			try {
				Statement st = db.createStatement();
				ResultSet rs = st.executeQuery("DELETE FROM \"Notes\" WHERE name='" + name + "'");
			} catch (SQLException e) {
			} catch (Exception a) {
				a.printStackTrace();
			}

		}
	}

	// поиск папки
	public Folder FindFolder(String name) {
		// запрашиваем данные о репозиториях
		if (connection_status) {
			try {
				Statement st = db.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM \"Folders\"");
				ResultSet vl = rs;
				List<String> allRows = new ArrayList<String>();
				String nm;
				String pf;
				while (vl.next()) {
					nm = vl.getString("name");
					pf = vl.getString("parent_folder");
					if (nm.equals(name)) {
						if (pf != null)
							return (new Folder(nm, FindFolder(pf)));
						else
							return (new Folder(nm, null));
					}
				}
			} catch (SQLException e) {

			} catch (Exception a) {
				a.printStackTrace();
			}

		}
		return null;
	};
}
