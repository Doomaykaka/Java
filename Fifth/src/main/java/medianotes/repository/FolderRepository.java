package main.java.medianotes.repository;

import java.util.Set;

import main.java.medianotes.model.Folder;

//интерфейс для хранилища папок
public interface FolderRepository {
	//описание методов класса
	Set<Folder> getFolders();  //описание метода получения всех папок
	void CreateFolder(String name,Folder parentFolder);  //описание метода создания папки
	void RemoveFolder(String name,Folder parentFolder);  //описание метода удаления папки
	String GetPath(String name,Folder parentFolder);  //описание метода получения пути папки
	Folder FindFolder(String name);  //описание метода поиска папки
}
