package main.java.medianotes.model;

import java.util.Objects;

//класс папки
public class Folder {
	//поля класса
	
	private String name; //имя текущей папки
	private Folder parentFolder; //объект родительской папки
	
	//методы класса
	
	//конструктор класса (выполняется при создании объекта)
	public Folder(String name_in, Folder parentFolder_in){
		name = name_in;
		parentFolder = parentFolder_in;
	}
	//геттер имени текущей папки (получение имени текущей папки)
	public String getName() {
		return name;
	}
	//сеттер имени текущей папки (установка имени текущей папки)
	public void setName(String name) {
		this.name = name;
	}
	//геттер объекта родительской папки (получение объекта родительской папки)
	public Folder getParentFolder() {
		return parentFolder;
	}
	//сеттер объекта родительской папки (установка объекта родительской папки)
	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}
	//получение хэша
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
