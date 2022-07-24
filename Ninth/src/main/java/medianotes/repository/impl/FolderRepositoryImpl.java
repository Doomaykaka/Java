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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import main.java.medianotes.model.Folder;
import main.java.medianotes.repository.FolderRepository;

//класс хранилища папок
public class FolderRepositoryImpl implements FolderRepository, Serializable {
	// поля класса

	private static final long serialVersionUID = 1L;
	private String path; // строка для хранения пути
	private static Connection db = null; // объект подключения
	private static boolean connection_status = false; // логическая переменная хранящая статус подключения к бд

	// интерфейсы

	// функциональный интерфейс
	interface SortInt {
		// абстрактный метод
		Set<Folder> getSorted();
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
	public FolderRepositoryImpl() {
	}

	// геттер репозитория (получение репозитория)
	public Set<Folder> getFolders() {
		final Set<Folder> FOLDERS = new HashSet<>(); // множество
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
					FOLDERS.add(new Folder(nm, FindFolder(pf)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// сортировка списка папок
		SortInt si; // создаём объект функцианального интерфейса
		si = () -> FOLDERS.stream().sorted(Comparator.comparing(folder -> folder.getName()))
				.collect(Collectors.toSet()); // сортируем список папок при помощи лямбда-выражениия

		return si.getSorted();
	}

	// создание папки
	public void CreateFolder(String name, Folder parentFolder) {
		// добавляем данные о репозиториях
		if (connection_status) {
			try {
				name = name.toUpperCase();
				Statement st1 = db.createStatement();
				ResultSet rs1 = st1.executeQuery("SELECT count(*) FROM \"Folders\"");
				if (rs1.next()) {
					int count = rs1.getInt(1);
					System.out.println(Integer.toString(count));
					Statement st = db.createStatement();
					ResultSet rs = st.executeQuery("INSERT INTO \"Folders\" (id,name,parent_folder)" + "VALUES ("
							+ Integer.toString(count) + ", '" + name + "', '" + parentFolder.getName() + "')");
				}
			} catch (SQLException e) {

			} catch (Exception a) {
				a.printStackTrace();
			}

		}
	}

	// удаление папки
	public void RemoveFolder(String name, String parentFolderName) {
		// удаляем данные о репозиториях
		if (connection_status) {
			try {
				Statement st = db.createStatement();
				ResultSet rs = st.executeQuery("DELETE FROM \"Folders\" WHERE name='" + name + "' AND parent_folder='"
						+ parentFolderName + "'");
			} catch (SQLException e) {

			} catch (Exception a) {
				a.printStackTrace();
			}

		}
	}

	// получение пути к папке
	public String GetPath(String name, Folder parentFolder) {
		path = "";
		Folder fol = FindFolder(name);
		if (fol == null) {
			System.out.println("Folder not found"); // выводим информирующее сообщение
			return "";
		} else {
			PathGenRec(fol);
		}
		return path;
	}

	// рекурсивное построение пути
	private void PathGenRec(Folder fol) {
		if (fol != null) {
			path = fol.getName() + "/" + path;
			PathGenRec(fol.getParentFolder());
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
