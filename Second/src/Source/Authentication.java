package Source;
import java.util.Scanner;

public class Authentication {
	private static final String LOGIN = "petya";
	private static final String PASSWORD = "1234";
	
	public static int athentificate() {
		Scanner s = new Scanner(System.in);
		
		System.out.println("Login :");
		var login = s.nextLine();
		
		System.out.println("Password :");
		var password = s.nextLine();
		
		if((login.equals(LOGIN))&&(password.equals(PASSWORD))) {
			System.out.println("Hello "+LOGIN);
			System.out.println("New note : ");		
			
			
			String name;
			String text;
			String author;
			Note note1;
			System.out.println("Enter Note name - ");
			name = s.nextLine();
			System.out.println("Enter Note text - ");
			text = s.nextLine();
			System.out.println("Enter Note author - ");
			author = s.nextLine();
			note1 = new Note(name,text);
			System.out.println("Result : "+note1.getAuthor()+" "+note1.getName()+" "+note1.getText()+" "+note1.getCreationDate().toString());
			return 0;
		}else {
			System.out.println("Bad login or password");
			return 1;
		}
		
	}
}
