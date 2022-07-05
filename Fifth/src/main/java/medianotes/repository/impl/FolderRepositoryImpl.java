package main.java.medianotes.repository.impl;

import java.util.HashSet;
import java.util.Set;

import main.java.medianotes.model.Folder;
import main.java.medianotes.repository.FolderRepository;

//класс хранилища папок
public class FolderRepositoryImpl implements FolderRepository{
	//поля класса
	
	private static final Set<Folder> FOLDERS = new HashSet<>(); //множество , т.к. храним только уникальные заметки (паттерн singleton , т.к. за весь процесс работы приложения создаётся только один объект класса)
	private String path; //строка для хранения пути
	
	//методы класса
	
	//метод работы со статическими членами (запускается при старте приложения)
	static {
		FOLDERS.add(new Folder("root",null)); //создаём корневую директорию
	}
	//контруктор класса
	public FolderRepositoryImpl() {}
	//геттер репозитория (получение репозитория)
	public Set<Folder> getFolders() {
		return FOLDERS;
	}
	//создание папки
	public void CreateFolder(String name,Folder parentFolder) {
		FOLDERS.add(new Folder(name,parentFolder));
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

}
