package main.java.medianotes.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import main.java.medianotes.model.Folder;
import main.java.medianotes.repository.FolderRepository;

//класс хранилища папок
public class FolderRepositoryImpl implements FolderRepository,Serializable{
	//поля класса
	
	private static final long serialVersionUID = 1L;
	private static Set<Folder> FOLDERS = new HashSet<>(); //множество , т.к. храним только уникальные заметки (паттерн singleton , т.к. за весь процесс работы приложения создаётся только один объект класса)
	private String path; //строка для хранения пути
	
	//интерфейсы
	
	//функциональный интерфейс
	interface SortInt{
	    // абстрактный метод
		Set<Folder> getSorted();
	}
	
	//методы класса
	
	//метод работы со статическими членами (запускается при старте приложения)
	static {
		FOLDERS.add(new Folder("root",null)); //создаём корневую директорию
	}
	//контруктор класса
	public FolderRepositoryImpl() {
		String filename = "folders.dat"; //строка с именем файла с данными
		
		File dat = new File(filename); //проверяем наличие файла
		if(dat.exists()==false) {
			try {
				dat.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		readFoldersData();
	}
	//геттер репозитория (получение репозитория)
	public Set<Folder> getFolders() {
		return FOLDERS;
	}
	//создание папки
	public void CreateFolder(String name,Folder parentFolder) {
		FOLDERS.add(new Folder(name,parentFolder));
		//сортировка списка папок
		SortInt si; //создаём объект функцианального интерфейса
		si=()->FOLDERS.stream().sorted(Comparator.comparing(folder->folder.getName())).collect(Collectors.toSet()); //сортируем список папок при помощи лямбда-выражениия
		FOLDERS=si.getSorted();
		writeFoldersData();
	}
	//удаление папки
	public void RemoveFolder(String name,Folder parentFolder) {
		Folder fol = FindFolder(name);
		if(fol!=null) {
			FOLDERS.remove(fol);
			System.out.println("Folder removed");
		}else{
			System.out.println("Folder not exist");
		}
		writeFoldersData();
	}
	//получение пути к папке
	public String GetPath(String name,Folder parentFolder) {
		path="";
		Folder fol = FindFolder(name);
		if(fol==null) {
			System.out.println("Folder not found"); //выводим информирующее сообщение
			return "";
		}else {
			PathGenRec(fol);
		}
		return path;
	}
	//рекурсивное построение пути
	private void PathGenRec(Folder fol) {
		if(fol!=null) {
			path=fol.getName()+"/"+path;
			PathGenRec(fol.getParentFolder());
		}
	}
	//поиск папки
	public Folder FindFolder(String name) {
		for(var fal : FOLDERS) {
			if(fal.getName().equals(name)) {
				return fal;
			}
		}
		return null;
	};
	//метод класса отвечающий за чтение файла с папками
	public static void readFoldersData() {	
		String filename = "folders.dat"; //строка с именем файла с папками
		boolean checkSize = false; //логическая переменная хранящая результат проверки файла на заполненность
		
		File dat = new File(filename); //проверяем размер файла
		if(dat.length()!=0) {
			checkSize=true;
		}
		
		if(checkSize) {
			try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filename))){ //чтение данных о папках	
				FOLDERS = (Set<Folder>) stream.readObject(); //неправильный тип
			}catch(IOException e) {
				throw new RuntimeException("Read error"); //выбрасываем исключение сигнализирующее о ошибке чтения файла
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("ReadObject error"); //выбрасываем исключение сигнализирующее о ошибке получения объекта
			}
		}
	}
	//метод класса отвечающий за запись в файл с папками
	public static void writeFoldersData() {	
		String filename = "folders.dat"; //строка с именем файла с папками
			
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename))){ //запись данных о папках
			stream.writeObject(FOLDERS);
		}catch(IOException e) {
			throw new RuntimeException("Read error"); //выбрасываем исключение сигнализирующее о ошибке записи файла
		}
	}
}

