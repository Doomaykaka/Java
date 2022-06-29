package Source;
import java.util.Scanner;

import Source.command.CommandReader;
//класс отвечающий за аутентификацию
public class Authentication {
	//поля класса
	
	private static final String LOGIN = "petya"; //поле с логином
	private static final String PASSWORD = "1234"; //поле с паролем
	
	//методы класса 
	
	//метод класса отвечающий за аутентификацию
	public static int athentificate() {
		try (Scanner s = new Scanner(System.in)) {
			System.out.println("Login : "); //выводим надпись и считываем логин пользователя
			var login = s.nextLine();
			
			System.out.println("Password : "); //выводим надпись и считываем пароль пользователя
			var password = s.nextLine();
			
			if((login.equals(LOGIN))&&(password.equals(PASSWORD))) { //проверяем данные аутентификации введённые пользователем
				System.out.println("Hello "+LOGIN); //выводим приветствующую надпись	
				
				int status = CommandReader.startReadCommand(); //вызываем метод консольного интерфейса 
				return status; //возвращаем статус приложения
			}else {
				System.out.println("Bad login or password"); //выводим надпись о неверных данных аккаунта
				return 1; //завершаем приложение
			}
		}
		
	}
}
