package Source.repository.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Source.Patient;
import Source.repository.PatientRepository;

//класс хранилища пациентов
public class PatientRepositoryImpl implements PatientRepository{
	//поля класса
	
	private static List<Patient> PATIENTS = new LinkedList<>(); //список со всеми пациентами
		
	//методы класса
		
	//Конструктор класса
	public PatientRepositoryImpl() {
		File pat = new File("pati");
		if(pat.exists()==false) {
			try {
				pat.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		readPatients();
	}
	//Очистка файла с ппациентами
	public void WritingZero() {
		try {
			FileWriter patiCZ = new FileWriter("pati",false);
			patiCZ.write("");
			patiCZ.flush();
			patiCZ.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Запись пациентов в файл
	public void savePatients() {
		WritingZero();
		try {
			FileWriter patiW = new FileWriter("pati",true);
			for(var pati : PATIENTS) {
				String Id = Integer.toString(pati.getId());
				String FullName = pati.getFullName();
				patiW.write(Id+'\n');
				patiW.write(FullName+'\n');	
			}
			patiW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	//Чтение пациентов из файла
	public void readPatients() {
		PATIENTS = new LinkedList<>();
		String id = "";
		String full_name = "";
		try (Scanner scan = new Scanner(new File("pati"))) { //читаем по две строки за раз
			while (scan.hasNextLine()) {
				id=scan.nextLine();
		        if(scan.hasNextLine()) {
		        	full_name=scan.nextLine();
		        }
		        PATIENTS.add(new Patient(Integer.parseInt(id),full_name));
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	};
	//Добавление пациента
	public Patient createPatient(Patient pat) {
		PATIENTS.add(pat);		
		savePatients();
		return pat;
	};
	//Редактирование данных пациента
	public void editPatient(int pat_id, String full_name_in) {
		int idx = -1;
		for(var pati : PATIENTS) { //перебираем данные о пациентах
			if(pati.getId()==pat_id) {
				idx=PATIENTS.indexOf(pati);
			}
		}
		if(idx!=-1) {
			PATIENTS.set(idx,new Patient(pat_id,full_name_in));
		}
		savePatients();
	};
	//Получение списка со всеми пациентами
	public List<Patient> getAllPatients(){
		return PATIENTS;
	};
	//Удаление пациента
	public void removePatient(int pat_id) {
		int idx = -1;
		for(var pati : PATIENTS) { //перебираем данные о пациентах
			if(pati.getId()==pat_id) {
				idx=PATIENTS.indexOf(pati);
			}
		}
		if(idx!=-1) {
			PATIENTS.remove(idx);
		}
		savePatients();
	};
}
