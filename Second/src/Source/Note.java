package Source;
import java.time.Instant;

//класс записки
public class Note {
	//поля класса
	
	private String name; //поле с названием записки
	private String text; //поле с текстом записки
	private String author; //поле с автором записки 
	private final Instant creationDate; //поле с датой создания записки 
	
	//методы класса 
	
	//конструктор класса (выполняется при создании объекта)
	public Note(String name_in, String text_in){
		name = name_in;
		text = text_in;
		
		author = null;
		
		creationDate = Instant.now();
	}
	//геттер названия записки (получение названия записки)
	public String getName() {
		return name;
	}
	//геттер текста записки (получение текста записки)
	public String getText() {
		return text;
	}
	//геттер автора записки (получение автора записки)
	public String getAuthor() {
		return author;
	}
	//геттер даты создания записки (получение даты создания записки)
	public Instant getCreationDate() {
		return creationDate;
	}
	//сеттер названия записки (установка названия записки)
	public void setName(String newName) {
		name=newName;
	}
	//сеттер текста записки (установка текста записки)
	public void setText(String newText) {
		text=newText;
	}
	//сеттер автора записки (установка автора записки)
	public void setAuthor(String newAuthor) {
		author=newAuthor;
	}
	
}
