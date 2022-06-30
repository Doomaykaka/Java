package Source.repository.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Source.Appointments;
import Source.repository.AppointmentsRepository;

//класс хранилища приёмов
public class AppointmentsRepositoryImpl implements AppointmentsRepository{
	//поля класса
	
	private static List<Appointments> APPOINTMENTS = new LinkedList<>(); //список со всеми приёмами
	
	//методы класса
	
	//Конструктор класса
	public AppointmentsRepositoryImpl() {
		File appo = new File("appo");
		if(appo.exists()==false) {
			try {
				appo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		readAppointments();
	}
	//Очистка файла с приёмами
	public void WritingZero() {
			try {
				FileWriter appoCZ = new FileWriter("appo",false);
				appoCZ.write("");
				appoCZ.flush();
				appoCZ.close();
		      } catch (IOException e) {
				e.printStackTrace();
		      }
	}
	//Запись приёмов в файл
	public void saveAppointments() {
		WritingZero();
		try {
			FileWriter appoW = new FileWriter("appo",true);
			for(var appo : APPOINTMENTS) {
				String AppointmentId = Integer.toString(appo.getAppointmentId());
				String PatientId = Integer.toString(appo.getPatientId());
				String RegistrationDate = appo.getRegistrationDate();
				String ReceiptDate = appo.getReceiptDate();
				String ReceptionStatus = Integer.toString(appo.getReceptionStatus());
				String DoctorId = Integer.toString(appo.getDoctorId());
				appoW.write(AppointmentId+'\n');
				appoW.write(PatientId+'\n');
				appoW.write(RegistrationDate+'\n');
				appoW.write(ReceiptDate+'\n');
				appoW.write(ReceptionStatus+'\n');
				appoW.write(DoctorId+'\n');	
			}
			appoW.close();
	      } catch (IOException e) {
			e.printStackTrace();
	      }
	};
	//Чтение приёмов из файла
	public void readAppointments() {
		APPOINTMENTS = new LinkedList<>();
		String appo_id = "";
		String patient_id = "";
		String registrationDate = "";
		String receiptDate = "";
		String receptionStatus = "";
		String doctor_id = "";
		try (Scanner scan = new Scanner(new File("appo"))) { //читаем по шесть строк за раз
	            while (scan.hasNextLine()) {
	            	appo_id=scan.nextLine();
	            	if(scan.hasNextLine()) {
	            		patient_id=scan.nextLine();
	            		if(scan.hasNextLine()) {
	            			registrationDate=scan.nextLine();
	            			if(scan.hasNextLine()) {
	            				receiptDate=scan.nextLine();
	            				if(scan.hasNextLine()) {
	            					receptionStatus=scan.nextLine();
	            					if(scan.hasNextLine()) {
	            						doctor_id=scan.nextLine();
	            					}
	            				}
	            			}
	            		}
	            	}
	            	APPOINTMENTS.add(new Appointments(Integer.parseInt(appo_id),Integer.parseInt(patient_id),registrationDate,receiptDate,Integer.parseInt(receptionStatus),Integer.parseInt(doctor_id)));
	            }
	    } catch (FileNotFoundException e) {
	            e.printStackTrace();
	    }
	};
	//Создание приёма 
	public Appointments createAppointment(Appointments app) {
		APPOINTMENTS.add(app);		
		saveAppointments();
		return app;
	}
	//Редактирование приёма
	public void editAppointment(int appo_id, int patient_id_in, String registrationDate_in, String receiptDate_in, int receptionStatus_in, int doctor_id_in) {
		int idx = -1;
		for(var appo : APPOINTMENTS) { //перебираем данные о приёмах
			if(appo.getAppointmentId()==appo_id) {
				idx=APPOINTMENTS.indexOf(appo);
			}
		}
		if(idx!=-1) {
			APPOINTMENTS.set(idx,new Appointments(appo_id,patient_id_in,registrationDate_in,receiptDate_in,receptionStatus_in,doctor_id_in));
		}
		saveAppointments();
	}
	//Получение списка приёмов
	public List<Appointments> getAllAppointments(){
		return APPOINTMENTS;
	}
	//Удаление приёма
	public void removeAppointment(int appo_id) {
		int idx = -1;
		for(var appo : APPOINTMENTS) { //перебираем данные о приёмах
			if(appo.getAppointmentId()==appo_id) {
				idx=APPOINTMENTS.indexOf(appo);
			}
		}
		if(idx!=-1) {
			APPOINTMENTS.remove(idx);
		}
		saveAppointments();
	}
}
