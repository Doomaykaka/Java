package Source.repository;

import java.util.List;

import Source.Patient;

//интерфейс для хранилища пациентов
public interface PatientRepository {
	//описание методов класса	
	Patient createPatient(Patient pat);  //описание метода добавления пациента
			
	void editPatient(int pat_id, String full_name_in);  //описание метода редактирования данных пациента
			
	List<Patient> getAllPatients();  //описание метода получения списка со всеми пациентами
			
	void removePatient(int pat_id);  //описание метода удаления пациента
}
