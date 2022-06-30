package Source;

//класс пациентов
public class Patient {
	//поля класса
	
	private int id; //поле с id пациента ветклиники
	private String full_name; //поле с фио пациента ветклиники
	
	//методы класса 
	
	//конструктор класса (выполняется при создании объекта)
	public Patient(int id_in, String full_name_in){
		id = id_in;
		full_name = full_name_in;	
	}
	//геттер id пациента (получение id пациента)
	public int getId() {
		return id;
	}
	//геттер фио пациента (получение фио пациента)
	public String getFullName() {
			return full_name;
	}
	//сеттер id пациента (установка id пациента)
	public void setId(int newId) {
		id=newId;
	}
	//сеттер фио пациента (установка фио пациента)
	public void setFullName(String newFull_name) {
		newFull_name=full_name;
	}
}
