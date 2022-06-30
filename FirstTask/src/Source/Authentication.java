package Source;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Source.command.CommandReader;

//класс отвечающий за аутентификацию
public class Authentication {
	//поля класса
	
	private static final List<String> ACCDAT = new LinkedList<>(); //список со всеми данными администраторов системы
	
	//методы класса 
	
	//метод считывания списка администраторов
	public static int athentificateReadAccDat() {
		String login = "";
		String password = "";
		try (Scanner scan = new Scanner(new File("sys_dat"))) { //читаем по две строки за раз (первая логин , вторая пароль)
	            while (scan.hasNextLine()) {
	            	login=scan.nextLine();
	            	if(scan.hasNextLine()) {
	            		password=scan.nextLine();
	            		ACCDAT.add(login+" "+password);
	            	}
	            }
	    } catch (FileNotFoundException e) {
	            e.printStackTrace();
	    }
		return 0;
	}
	
	//метод класса отвечающий за аутентификацию
	public static int athentificate() {
		var login = "";
		var password = "";
		boolean status = false;
		
		try (Scanner s = new Scanner(System.in)) {
			System.out.println("Login : "); //выводим надпись и считываем логин пользователя
			login = s.nextLine();
			
			System.out.println("Password : "); //выводим надпись и считываем пароль пользователя
			password = s.nextLine();
			
			//проверяем наличие данных аутентификации в базе
			for(var data : ACCDAT) { //перебираем данные аутентификации в базе и сверяем с введенными данными
				String[] account = new String[2];
				account=data.split(" ");
				if((account[0].equals(login))&&(account[1].equals(password))) {
					status=true;
				}else{
					status=false;
				}
			}
			
			if(status){ //проверяем статус входа в систему
				System.out.println("Hello "+login); //выводим приветствующую надпись	
				
				int CMDstatus = CommandReader.startReadCommand(); //вызываем метод консольного интерфейса 
				return CMDstatus; //возвращаем статус приложения
			}else {
				System.out.println("Bad login or password"); //выводим надпись о неверных данных аккаунта
				return 1; //возвращаем статус приложения
			}
		}
		
	}
}
