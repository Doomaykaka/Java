package Source.repository.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Source.Doctor;
import Source.repository.DoctorRepository;

//класс хранилища докторов
public class DoctorRepositoryImpl implements DoctorRepository{
	//поля класса
	
	private static List<Doctor> DOCTORS = new LinkedList<>(); //список со всеми докторами
	
	//методы класса
	
	//Конструктор класса
	public DoctorRepositoryImpl() {
		File doc = new File("doc");
		if(doc.exists()==false) {
			try {
				doc.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		readDoctors();
	}
	//Очистка файла с докторами
	public void WritingZero() {
		try {
			FileWriter docCZ = new FileWriter("doc",false);
			docCZ.write("");
			docCZ.flush();
			docCZ.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	//Запись докоров в файл
	public void saveDoctors() {
		WritingZero();
		try {
			FileWriter docW = new FileWriter("doc",true);
			for(var doc : DOCTORS) {
				String Id = Integer.toString(doc.getId());
				String FullName = doc.getFullName();
				docW.write(Id+'\n');
				docW.write(FullName+'\n');	
			}
			docW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	//Чтение докоров из файла
	public void readDoctors() {
		DOCTORS = new LinkedList<>();
		String id = "";
		String full_name = "";
		try (Scanner scan = new Scanner(new File("doc"))) { //читаем по две строки за раз
			while (scan.hasNextLine()) {
				id=scan.nextLine();
				if(scan.hasNextLine()) {
			        full_name=scan.nextLine();
				}
			    DOCTORS.add(new Doctor(Integer.parseInt(id),full_name));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	};
	//Добавление доктора
	public Doctor createDoctor(Doctor doc) {
		DOCTORS.add(doc);		
		saveDoctors();
		return doc;
	};
	//Редактирование данных доктора
	public void editDoctor(int doc_id, String full_name_in) {
		int idx = -1;
		for(var doc : DOCTORS) { //перебираем данные о докторах
			if(doc.getId()==doc_id) {
				idx=DOCTORS.indexOf(doc);
			}
		}
		if(idx!=-1) {
			DOCTORS.set(idx,new Doctor(doc_id,full_name_in));
		}
		saveDoctors();
	};
	//Получение списка со всеми докторами
	public List<Doctor> getAllDoctors(){
		return DOCTORS;
	};
	//Удаление доктора
	public void removeDoctor(int doc_id) {
		int idx = -1;
		for(var doc : DOCTORS) { //перебираем данные о докторах
			if(doc.getId()==doc_id) {
				idx=DOCTORS.indexOf(doc);
			}
		}
		if(idx!=-1) {
			DOCTORS.remove(idx);
		}
		saveDoctors();
	};
}
