package Source;
import java.util.Scanner;
//класс отвечающий за аутентификацию
public class Authentication {
	//поля класса
	
	private static final String LOGIN = "petya"; //поле с логином
	private static final String PASSWORD = "1234"; //поле с паролем
	
	//методы класса 
	
	//метод класса отвечающий за аутентификацию
	public static int athentificate() {
		Scanner s = new Scanner(System.in); //создаём объект Scanner для чтения потока ввода из командной строки
		
		System.out.println("Login :"); //выводим надпись и считываем логин пользователя
		var login = s.nextLine();
		
		System.out.println("Password :"); //выводим надпись и считываем пароль пользователя
		var password = s.nextLine();
		
		if((login.equals(LOGIN))&&(password.equals(PASSWORD))) { //проверяем данные аутентификации введённые пользователем
			System.out.println("Hello "+LOGIN); //выводим приветствующую надпись
			System.out.println("New note : ");		
			
			
			String name; //объявляем переменные в которые будут считаны данные о записке
			String text;
			String author;
			Note note1; //создаём объект записки
			System.out.println("Enter Note name - "); //инициализируем переменные
			name = s.nextLine();
			System.out.println("Enter Note text - ");
			text = s.nextLine();
			System.out.println("Enter Note author - ");
			author = s.nextLine();
			note1 = new Note(name,text); //заполняем поля объекта
			System.out.println("Result : "+note1.getAuthor()+" "+note1.getName()+" "+note1.getText()+" "+note1.getCreationDate().toString()); //выводим результат
			return 0; //завершаем приложение
		}else {
			System.out.println("Bad login or password"); //выводим надпись о неверных данных аккаунта
			return 1; //завершаем приложение
		}
		
	}
}
