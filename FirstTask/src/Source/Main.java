package Source;

public class Main  extends Authentication {
	//точка входа в приложение
	public static void main(String[] args) {	
		Authentication.athentificateReadAccDat(); //Вызываем метод athentificate для получения списка администраторов
		Authentication.athentificate(); //Вызываем метод athentificate
	}
}
