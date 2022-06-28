package Source;
import java.time.Instant;

public class Note {
	private String name;
	private String text;
	private String author;
	private final Instant creationDate;
	
	public Note(String name_in, String text_in){
		name = name_in;
		text = text_in;
		
		author = null;
		
		creationDate = Instant.now();
	}
	
	public String getName() {
		return name;
	}
	
	public String getText() {
		return text;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public Instant getCreationDate() {
		return creationDate;
	}
	
	public void setName(String newName) {
		name=newName;
	}
	
	public void setText(String newText) {
		text=newText;
	}
	
	public void setAuthor(String newAuthor) {
		author=newAuthor;
	}
	
}
