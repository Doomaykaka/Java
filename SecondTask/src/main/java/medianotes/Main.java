package main.java.medianotes;

public class Main extends Authentication {

	public static void main(String[] args) {	
		try {
			Authentication.athentificate(); //Вызываем метод athentificate
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			System.out.println("Program is closed"); //выводим надпись о завершении работы приложения		
		}
	}
}
