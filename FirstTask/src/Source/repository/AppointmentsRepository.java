package Source.repository;

import java.util.List;

import Source.Appointments;

//интерфейс для хранилища приёмов
public interface AppointmentsRepository {
	//описание методов класса		
	Appointments createAppointment(Appointments app);  //описание метода создания приёма
	
	void editAppointment(int appo_id, int patient_id_in, String registrationDate_in, String receiptDate_in, int receptionStatus_in, int doctor_id_in);  //описание метода редактирования приёма
	
	List<Appointments> getAllAppointments();  //описание метода получения списка со всеми приёмами
	
	void removeAppointment(int appo_id);  //описание метода удаления приёма
}
