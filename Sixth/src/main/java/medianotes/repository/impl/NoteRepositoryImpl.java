package main.java.medianotes.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import main.java.medianotes.model.Folder;
import main.java.medianotes.model.Note;
import main.java.medianotes.repository.NoteRepository;
import main.java.medianotes.repository.impl.FolderRepositoryImpl.SortInt;

//класс хранилища записок
public class NoteRepositoryImpl implements NoteRepository,Serializable{
	//поля класса
	
	private static final long serialVersionUID = 1L;
	private static List<Note> NOTES = new LinkedList<>(); //список со всеми записками
	
	//интерфейсы
	
	//функциональный интерфейс
	interface SortInt{
		// абстрактный метод
		List<Note> getSorted();
	}
	
	//методы класса
	
	//контруктор класса
	public NoteRepositoryImpl() {
		String filename = "notes.dat"; //строка с именем файла с данными
			
		File dat = new File(filename); //проверяем наличие файла
		if(dat.exists()==false) {
			try {
				dat.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		readNotesData(); 
	}
	//метод сохранения записки
	@Override
	public Note save(Note note) {
		NOTES.add(note);
		//сортировка списка записок
		SortInt si; //создаём объект функцианального интерфейса
		si=()->NOTES.stream().sorted(Comparator.comparing(notes->notes.getName())).collect(Collectors.toList()); //сортируем список записок при помощи лямбда-выражения
		NOTES=si.getSorted();
		writeNotesData();
		
		return note;
	}
	//метод получения списка со всеми записками
	@Override
	public List<Note> getAllNotes() {
		return NOTES;
	}
	//метод удаления записки
	@Override
	public void remove(String name) {
		int ind; //создаём переменные для хранения индекса нужной записки
		ind=0;
		int rind;
		rind=-1;
		for(var note : NOTES) { //перебираем записки и ищем совпадение по имени
			if(note.getName().equals(name)) {
				rind=ind;
			}
			ind=ind+1;
		}
		if(rind!=-1) { //если совпадение есть удаляем , иначе выводим сообщение о неудаче
			NOTES.remove(rind);
			System.out.printf("Note %s removed \n",name);
		}else{
			System.out.printf("Note %s was not found \n",name);
		}
		writeNotesData();
	}
	//метод класса отвечающий за чтение файла с записками
	public static void readNotesData() {	
		String filename = "notes.dat"; //строка с именем файла с записками
		boolean checkSize = false; //логическая переменная хранящая результат проверки файла на заполненность
		
		File dat = new File(filename); //проверяем размер файла
		if(dat.length()!=0) {
			checkSize=true;
		}
		
		if(checkSize) {
			try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filename))){ //чтение данных о записках
				NOTES = (List<Note>) stream.readObject();	
			}catch(IOException e) {
				throw new RuntimeException("Read error"); //выбрасываем исключение сигнализирующее о ошибке чтения файла
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("ReadObject error"); //выбрасываем исключение сигнализирующее о ошибке получения объекта
			}
		}
	}
	//метод класса отвечающий за запись в файл с записками
	public static void writeNotesData() {	
		String filename = "notes.dat"; //строка с именем файла с записками
				
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename))){ //запись данных о записках
			stream.writeObject(NOTES);
		}catch(IOException e) {
			throw new RuntimeException("Read error"); //выбрасываем исключение сигнализирующее о ошибке записи файла
		}
	}
}
