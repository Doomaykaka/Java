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
	//метод класса отвечающий за запрос данных аккаунтов из бд
	public static void readAuthentificationData() {		
	    boolean connection_status = false; //логическая переменная хранящая статус подключения к бд
	    Connection db = null; //объект подключения
	    String db_type = "postgresql";
	    String db_address = "localhost";
	    String db_port = "5432";
	    String db_name = "Seventh";
	    String db_user = "postgres";
	    String db_password = "FireStarter11";
		
	    //подключаемся к бд
	    try {
            DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:"+db_type+"://").  
                    append(db_address+":").         
                    append(db_port+"/").               
                    append(db_name+"?").             
                    append("user="+db_user+"&").      
                    append("password="+db_password);     

            db = DriverManager.getConnection(url.toString());
            if(db!=null) {
            	connection_status=true;
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
		
	    //запрашиваем данные аккаунтов
	    if(connection_status) {
	    	try {
	    		Statement st = db.createStatement();
		    	ResultSet rs = st.executeQuery("SELECT * FROM \"Auths\"");
		    	ResultSet vl = rs;
		    	List <String> allRows = new ArrayList<String>();
		    	String acc;
		    	while(vl.next()) {
		    		acc=vl.getString("login_password");
		    		allRows.add(acc);
		    	}
		    	LOGINandPASSWORD = allRows.toArray(new String[0]);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	    }
	}
	//метод класса отвечающий за поиск введённых данных в базе
	public static boolean findAccount(String accdat) {
		boolean finded = false; //логическая переменная хранящая результат поиска объекта
		
		for(int i=0; i<LOGINandPASSWORD.length;i++) { //сравниваем данные аккаунтов с введёнными
			if(LOGINandPASSWORD[i].equals(accdat)) {
				finded = true;
			}
		}
		
		return finded; //возвращаем результат поиска
	}
}
