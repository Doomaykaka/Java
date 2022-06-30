package Source.repository;

import java.util.List;

import Source.Doctor;

//интерфейс для хранилища докторов
public interface DoctorRepository {
	//описание методов класса	
	Doctor createDoctor(Doctor doc);  //описание метода добавления доктора
		
	void editDoctor(int doc_id, String full_name_in);  //описание метода редактирования данных доктора
		
	List<Doctor> getAllDoctors();  //описание метода получения списка со всеми докторами
		
	void removeDoctor(int doc_id);  //описание метода удаления доктора
}
