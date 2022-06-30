package Source;

//класс приёмов
public class Appointments {
	//поля класса
	
	private int appo_id; //поле с id приёма
	private int patient_id; //поле с id пациента ветклиники
	private String registrationDate; //поле с датой регистрации питомца
	private int receptionStatus; //поле со статусом приёма 
	private String receiptDate; //поле с датой поступления пациента
	private int doctor_id; //поле с id доктора ветклиники
	
	//методы класса
	
	//конструктор класса (выполняется при создании объекта)
	public Appointments(int appo_id_in, int patient_id_in, String registrationDate_in, String receiptDate_in, int receptionStatus_in, int doctor_id_in){
		appo_id = appo_id_in;
		patient_id = patient_id_in;
		registrationDate = registrationDate_in;
		receiptDate = receiptDate_in;
		receptionStatus=receptionStatus_in;
		doctor_id=doctor_id_in;
	}
	//геттер id приёма (получение id приёма)
	public int getAppointmentId() {
			return appo_id;
	}
	//геттер id пациента (получение id пациента)
	public int getPatientId() {
		return patient_id;
	}
	//геттер даты регистрации питомца (получение даты регистрации питомца)
	public String getRegistrationDate() {
		return registrationDate;
	}
	//геттер статуса приёма (получение статуса приёма)
	public int getReceptionStatus() {
		return receptionStatus;
	}
	//геттер даты поступления пациента (получение даты поступления пациента)
	public String getReceiptDate() {
		return receiptDate;
	}
	//геттер id доктора (получение id доктора)
	public int getDoctorId() {
		return doctor_id;
	}
	//сеттер id приёма (установка id приёма)
	public void setAppointmentId(int newAppo_id) {
		appo_id=newAppo_id;
	}
	//сеттер id пациента (установка id пациента)
	public void setPatientId(int newPatient_id) {
		patient_id=newPatient_id;
	}
	//сеттер даты регистрации питомца (установка даты регистрации питомца)
	public void setRegistrationDate(String newRegistrationDate) {
		registrationDate=newRegistrationDate;
	}
	//сеттер статуса приёма (установка статуса приёма)
	public void setReceptionStatus(int receptionStatus_in) {
		receptionStatus=receptionStatus_in;
	}
	//сеттер даты поступления пациента (установка даты поступления пациента)
	public void setReceiptDate(String receiptDate_in) {
		receiptDate=receiptDate_in;
	}
	//сеттер id доктора (установка id доктора)
	public void setDoctorId(int newDoctor_id) {
		doctor_id=newDoctor_id;
	}
}
