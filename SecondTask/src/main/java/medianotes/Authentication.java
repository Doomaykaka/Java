package main.java.medianotes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.medianotes.command.CommandReader;

//класс отвечающий за аутентификацию
public class Authentication {
	// поля класса

	private static String[] vLOGINandPASSWORDandID = {}; // поле с массивом данных аккаунтов

	// методы класса

	// метод класса отвечающий за аутентификацию
	public static void athentificate() {
		readAuthentificationData(); // читаем данные аккаунтов

		try (Scanner s = new Scanner(System.in)) {
			System.out.println("Choose operation (LogIn-0,Register-1) : "); // выводим надпись и считываем режим
			int operation = Integer.parseInt(s.nextLine());
			if(operation==0) {
				System.out.println("Login : "); // выводим надпись и считываем логин пользователя
				var login = s.nextLine();
				
				System.out.println("Password : "); // выводим надпись и считываем пароль пользователя
				var password = s.nextLine();

				int user_id = findAccount(login + " " + password);
				if (user_id != -1) { // проверяем данные аутентификации введённые пользователем
					System.out.println("Hello " + login); // выводим приветствующую надпись

					CommandReader.startReadCommand(user_id); // вызываем метод консольного интерфейса
				} else {
					throw new RuntimeException("Login failed"); // выбрасываем исключение сигнализирующее о неверных
																// данных аккаунта
				}
			}else{
				System.out.println("Login : "); // выводим надпись и считываем логин пользователя
				var login = s.nextLine();

				System.out.println("Password : "); // выводим надпись и считываем пароль пользователя
				var password1 = s.nextLine();

				System.out.println("Repeat password : "); // выводим надпись и считываем повторно введённый пароль
															// пользователя
				var password2 = s.nextLine();

				if (password1.equals(password2)) { // проверяем правильность пароля
					boolean loginUsingLever;
					loginUsingLever = false;

					for (String acc : vLOGINandPASSWORDandID) {
						String[] data = acc.split(" ");
						System.out.println(acc);
						if (data[0].equals(login)) {
							loginUsingLever = true;
						}
					}

					if (loginUsingLever) {
						throw new RuntimeException("The login is already in use"); // выбрасываем исключение
																					// сигнализирующее о неверных данных
																					// аккаунта
					} else {
						registerAccount(login+" "+password1); // регистрируем аккаунт
					}
				} else {
					throw new RuntimeException("Bad password"); // выбрасываем исключение сигнализирующее о неверных
																// данных аккаунта
				}
		
			}

		}catch(Exception e) {
			System.out.println("Some problems");
		}

	}

	// метод класса отвечающий за подключение к бд
	public static Connection getConnection() {
		Connection db = null; // объект подключения
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

		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println("Some problems");
		}

		return db;
	}

	// метод класса отвечающий за запрос данных аккаунтов из бд
	public static void readAuthentificationData() {
		boolean connection_status = false; // логическая переменная хранящая статус подключения к бд
		Connection db = getConnection(); // объект подключения

		if (db != null) {
			connection_status = true;
		}
		// запрашиваем данные аккаунтов
		if (connection_status) {
			try {
				Statement st = db.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM \"Auths\"");
				ResultSet vl = rs;
				List<String> allRows = new ArrayList<String>();
				String acc;
				String id;
				while (vl.next()) {
					acc = vl.getString("login_password");
					id = vl.getString("id");
					allRows.add(acc + " " + id);
				}
				vLOGINandPASSWORDandID = allRows.toArray(new String[0]);
			} catch (Exception e) {
				System.out.println("Some problems");
			}

		}
	}

	// метод класса отвечающий за поиск введённых данных в базе
	public static int findAccount(String accdat) {
		int finded_id = -1; // переменная хранящая id результата поиска объекта
		String LOGINandPASSWORD = ""; // переменная хранящая логин и пароль аккаунта

		for (int i = 0; i < vLOGINandPASSWORDandID.length; i++) { // сравниваем данные аккаунтов с введёнными
			String[] words = vLOGINandPASSWORDandID[i].split(" ");
			LOGINandPASSWORD = words[0] +" "+ words[1];
			if (LOGINandPASSWORD.equals(accdat)) {
				finded_id = Integer.parseInt(words[2]);
			}
		}

		return finded_id; // возвращаем результат поиска
	}

	// метод класса отвечающий за регистрацию аккаунта в бд
	public static void registerAccount(String accdat) {
		boolean connection_status = false; // логическая переменная хранящая статус подключения к бд
		Connection db = getConnection(); // объект подключения

		if (db != null) {
			connection_status = true;
		}
		// отправляем данные аккаунта
		if (connection_status) {
			try {
				Statement st1 = db.createStatement();
				ResultSet rs1 = st1.executeQuery("SELECT count(*) FROM \"Auths\"");
				if (rs1.next()) {
					int count = rs1.getInt(1);
					Statement st2 = db.createStatement();
					st2.addBatch("INSERT INTO \"Auths\" (id,login_password)" 
							+ "VALUES ("+ Integer.toString(count) +",\'"+accdat+"\')");
					st2.executeBatch();
					athentificate();
				}
			}catch (SQLException e) {
				System.out.println("Some problems");
			}catch(Exception a) {
				System.out.println("Some problems");
			}
		}
	}
}
