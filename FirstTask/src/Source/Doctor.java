package Source;

//класс докторов
public class Doctor {
	//поля класса
	
	private int id; //поле с id доктора ветклиники
	private String full_name; //поле с фио доктора ветклиники
		
	//методы класса 
	
	//конструктор класса (выполняется при создании объекта)
	public Doctor(int id_in, String full_name_in){
			id = id_in;
			full_name = full_name_in;	
	}
	//геттер id доктора (получение id доктора)
	public int getId() {
		return id;
	}
	//геттер фио доктора (получение фио доктора)
	public String getFullName() {
		return full_name;
	}
	//сеттер id доктора (установка id доктора)
	public void setId(int newId) {
		id=newId;
	}
	//сеттер фио доктора (установка фио доктора)
	public void setFullName(String newFull_name) {
		newFull_name=full_name;
	}
}
