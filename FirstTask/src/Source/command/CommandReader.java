package Source.command;

import java.util.Scanner;

import Source.Appointments;
import Source.Doctor;
import Source.Patient;
import Source.repository.AppointmentsRepository;
import Source.repository.DoctorRepository;
import Source.repository.PatientRepository;
import Source.repository.impl.AppointmentsRepositoryImpl;
import Source.repository.impl.DoctorRepositoryImpl;
import Source.repository.impl.PatientRepositoryImpl;

//класс обработчик команд
public class CommandReader {
	//поля класса
	
	private static final AppointmentsRepository AppointmentRepository = new AppointmentsRepositoryImpl(); //создаём объект интерфейса для хранения приёмов
	private static final DoctorRepository DoctorRepository = new DoctorRepositoryImpl(); //создаём объект интерфейса для хранения докторов
	private static final PatientRepository PatientRepository = new PatientRepositoryImpl(); //создаём объект интерфейса для хранения пациентов
	
	//методы класса
	
	//метод класса отвечающий за алгоритм работы обработчика команд
	public static int startReadCommand() {
		System.out.println("Programm started! Write your command: "); //выводим приветствующее сообщение
		
		try (Scanner s = new Scanner(System.in)) {
			while(true) { //создаём бесконечный цикл
				var text = s.nextLine(); //читаем команду
				int code = CommandReader.readCommand(text); //передаём команду в обработчик
				
				if(code == 0) { //проверяем событие выхода из приложения
					break;
				}
			}
		}
		return 0;//завершаем приложение
	}
	
	//метод класса отвечающий за обработку команд
	public static int readCommand(String command) {
		if(command.contains("create patient")) { //вызываем метод создания пациента и возвращаем статус
			return createPatient(command);
		}
		
		if(command.contains("edit patient")) { //вызываем метод редактирования пациента и возвращаем статус
			return editPatient(command);
		}
		
		if(command.contains("patients")) { //вызываем метод вывода пациентов и возвращаем статус
			return viewAllPatients(command);
		}
		
		if(command.contains("remove patient")) { //вызываем метод удаления пациента и возвращаем статус
			return removePatient(command);
		}
		
		if(command.contains("create doctor")) { //вызываем метод создания доктора и возвращаем статус
			return createDoctor(command);
		}
		
		if(command.contains("edit doctor")) { //вызываем метод редактирования доктора и возвращаем статус
			return editDoctor(command);
		}
		
		if(command.contains("doctors")) { //вызываем метод вывода докторов и возвращаем статус
			return viewAllDoctors(command);
		}
		
		if(command.contains("remove doctor")) { //вызываем метод удаления доктора и возвращаем статус
			return removeDoctor(command);
		}
		
		if(command.contains("create appointment")) { //вызываем метод создания приёма и возвращаем статус
			return createAppointment(command);
		}
		
		if(command.contains("edit appointment")) { //вызываем метод редактирования приёма и возвращаем статус
			return editAppointment(command);
		}
		
		if(command.contains("appointments")) { //вызываем метод вывода приёмов и возвращаем статус
			return viewAllAppointments(command);
		}
		
		if(command.contains("remove appointment")) { //вызываем метод удаления приёма и возвращаем статус
			return removeAppointment(command);
		}
		
		if(command.contains("help")) { //выводим справку команд и возвращаем статус
			System.out.println("Help :"); //выводим справку
			System.out.println("create patient");
			System.out.println("edit patient");
			System.out.println("patients");
			System.out.println("remove patient");
			System.out.println("create doctor");
			System.out.println("edit doctor");
			System.out.println("doctors");
			System.out.println("remove doctor");
			System.out.println("create appointment");
			System.out.println("edit appointment");
			System.out.println("appointments");
			System.out.println("remove appointment");
			System.out.println("help");
			System.out.println("exit");
			return 1;
		}
		
		if(command.contains("exit")) { //вызываем метод выхода из приложения и возвращаем статус
			return 0;
		}
		
		return -1; //возвращаем статус неправильной команды
	}
	
	//метод класса отвечающий за создание пациентов
	private static int createPatient(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
		
		int id = 0;	//создаём переменную для хранения id пациента
		String patientFullName = words[2]; //создаём строку для хранения фио пациента
		
		for(var pat : PatientRepository.getAllPatients()) {
			id=id+1;
		}
		
		PatientRepository.createPatient(new Patient(id,patientFullName)); //создаём пациента с полученными полями
		
		System.out.println("Patient created "); //выводим информирующее сообщение
		
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за редактирование пациентов
	private static int editPatient(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
			
		int id = Integer.parseInt(words[2]);	//создаём переменную для хранения id пациента
		String patientFullName = words[3]; //создаём строку для хранения фио пациента
			
		PatientRepository.editPatient(id,patientFullName); //редактируем пациента по полученным полям
			
		System.out.println("Patient redacted "); //выводим информирующее сообщение
			
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за вывод всех пациентов
	private static int viewAllPatients(String command) {
		System.out.println("Patients:"); //выводим информирующее сообщение
		
		for(var pat : PatientRepository.getAllPatients()) { //выводим всех пациентов
			System.out.printf("Id: %s , FullName: %s \n",pat.getId(),pat.getFullName());
		}
				
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за удаление пациента
	private static int removePatient(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
				
		int id = Integer.parseInt(words[2]);	//создаём переменную для хранения id пациента
				
		PatientRepository.removePatient(id); //удаляем пациента по полученным полям
				
		System.out.println("Patient removed "); //выводим информирующее сообщение
				
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за создание докторов
	private static int createDoctor(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
			
		int id = 0;	//создаём переменную для хранения id доктора
		String doctorFullName = words[2]; //создаём строку для хранения фио доктора
			
		for(var pat : DoctorRepository.getAllDoctors()) {
			id=id+1;
		}
			
		DoctorRepository.createDoctor(new Doctor(id,doctorFullName)); //создаём доктора с полученными полями
			
		System.out.println("Doctor created "); //выводим информирующее сообщение
			
		return 1; //возвращаем статус корректного выполнения
	}
		
	//метод класса отвечающий за редактирование докторов
	private static int editDoctor(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
				
		int id = Integer.parseInt(words[2]);	//создаём переменную для хранения id доктора
		String doctorFullName = words[3]; //создаём строку для хранения фио доктора
				
		DoctorRepository.editDoctor(id,doctorFullName); //редактируем доктора по полученным полям
				
		System.out.println("Doctor redacted "); //выводим информирующее сообщение
				
		return 1; //возвращаем статус корректного выполнения
	}
		
	//метод класса отвечающий за вывод всех докторов
	private static int viewAllDoctors(String command) {
		System.out.println("Doctors:"); //выводим информирующее сообщение
			
		for(var doc : DoctorRepository.getAllDoctors()) { //выводим всех докторов
			System.out.printf("Id: %s , FullName: %s \n",doc.getId(),doc.getFullName());
		}
					
		return 1; //возвращаем статус корректного выполнения
	}
		
	//метод класса отвечающий за удаление доктора
	private static int removeDoctor(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
					
		int id = Integer.parseInt(words[2]);	//создаём переменную для хранения id доктора
					
		DoctorRepository.removeDoctor(id); //удаляем доктора по полученным полям
					
		System.out.println("Doctor removed "); //выводим информирующее сообщение
					
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за создание приёмов
	private static int createAppointment(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
				
		int id = 0;	//создаём переменную для хранения id приёма
		int patientId = Integer.parseInt(words[3]); //создаём переменную для номера пациента записанного на приём
		String registrationDate = words[4]; //создаём строку для даты регистрации пациента
		String receiptDate = words[5]; //создаём строку для даты приёма пациента
		int receptionStatus = Integer.parseInt(words[6]); //создаём переменную для статуса приёма
		int doctorId = Integer.parseInt(words[7]); //создаём переменную для номера доктора к которому записались на приём
				
		for(var app : AppointmentRepository.getAllAppointments()) {
			id=id+1;
		}
				
		AppointmentRepository.createAppointment(new Appointments(id,patientId,registrationDate,receiptDate,receptionStatus,doctorId)); //создаём приём с полученными полями
				
		System.out.println("Appointment created "); //выводим информирующее сообщение
				
		return 1; //возвращаем статус корректного выполнения
	}
			
	//метод класса отвечающий за редактирование приёмов
	private static int editAppointment(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
					
		int id = Integer.parseInt(words[2]);	//создаём переменную для хранения id приёма
		int patientId = Integer.parseInt(words[3]); //создаём переменную для номера пациента записанного на приём
		String registrationDate = words[4]; //создаём строку для даты регистрации пациента
		String receiptDate = words[5]; //создаём строку для даты приёма пациента
		int receptionStatus = Integer.parseInt(words[6]); //создаём переменную для статуса приёма
		int doctorId = Integer.parseInt(words[7]); //создаём переменную для номера доктора к которому записались на приём
					
		AppointmentRepository.editAppointment(id,patientId,registrationDate,receiptDate,receptionStatus,doctorId); //редактируем приём по полученным полям
					
		System.out.println("Appointment redacted "); //выводим информирующее сообщение
					
		return 1; //возвращаем статус корректного выполнения
	}
			
	//метод класса отвечающий за вывод всех приёмов
	private static int viewAllAppointments(String command) {
		System.out.println("Appointments:"); //выводим информирующее сообщение
				
		for(var app : AppointmentRepository.getAllAppointments()) { //выводим все приёмы
			System.out.printf("AppointmentId: %s , PatientId: %s , RegistrationDate: %s , ReceiptDate: %s , ReceptionStatus: %s , DoctorId: %s \n",app.getAppointmentId(),app.getPatientId(),app.getRegistrationDate(),app.getReceiptDate(),app.getReceptionStatus(),app.getDoctorId());
		}
						
		return 1; //возвращаем статус корректного выполнения
	}
			
	//метод класса отвечающий за удаление приёма
	private static int removeAppointment(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
						
		int id = Integer.parseInt(words[2]);	//создаём переменную для хранения id приёма
						
		AppointmentRepository.removeAppointment(id); //удаляем приём по полученным полям
						
		System.out.println("Appointment removed "); //выводим информирующее сообщение
						
		return 1; //возвращаем статус корректного выполнения
	}
}
