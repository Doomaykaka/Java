package main.java.medianotes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import main.java.medianotes.command.CommandReader;
//класс отвечающий за аутентификацию
public class Authentication {
	//поля класса
	
	private static String [] LOGINandPASSWORD = {}; //поле с массивом данных аккаунтов
	
	//методы класса 
	
	//метод класса отвечающий за аутентификацию
	public static void athentificate() {
		readAuthentificationData(); //читаем данные аккаунтов
		
		try (Scanner s = new Scanner(System.in)) {			
			System.out.println("Login : "); //выводим надпись и считываем логин пользователя
			var login = s.nextLine();
			
			System.out.println("Password : "); //выводим надпись и считываем пароль пользователя
			var password = s.nextLine();
			
			if(findAccount(login+" "+password)) { //проверяем данные аутентификации введённые пользователем
				System.out.println("Hello "+login); //выводим приветствующую надпись	
				
				CommandReader.startReadCommand(); //вызываем метод консольного интерфейса 
			}else {
				throw new RuntimeException("Login failed"); //выбрасываем исключение сигнализирующее о неверных данных аккаунта
			}
		}
		
	}
	//метод класса отвечающий за чтение файла с данными аккаунтов
	public static void readAuthentificationData() {		
		String filename = "accounts.dat"; //строка с именем файла с данными
		
		File dat = new File(filename); //проверяем наличие файла
		if(dat.exists()==false) {
			try {
				dat.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String [] LOGINandPASSWORDtest = {"admin 1234","petya 1234"}; //строка со стартовыми аккаунтами
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename))){ //запись данных о аккаунтах
			stream.writeObject(LOGINandPASSWORDtest);
		}catch(IOException e) {
			throw new RuntimeException("Write error"); //выбрасываем исключение сигнализирующее о ошибке записи файла
		}			
		
		try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filename))){ //чтение данных о аккаунтах
			LOGINandPASSWORD = (String[]) stream.readObject();
		}catch(IOException e) {
			throw new RuntimeException("Read error"); //выбрасываем исключение сигнализирующее о ошибке чтения файла
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("ReadObject error"); //выбрасываем исключение сигнализирующее о ошибке получения объекта
		}
	}
	//метод класса отвечающий за поиск введённых данных в базе
	public static boolean findAccount(String accdat) {
		boolean finded = false; //логическая переменная хранящая результат поиска объекта
		
		for(int i=0; i<(LOGINandPASSWORD.length-1);i++) { //сравниваем данные аккаунтов с введёнными
			if(LOGINandPASSWORD[i].equals(accdat)) {
				finded = true;
			}
		}
		
		return finded; //возвращаем результат поиска
	}
}
