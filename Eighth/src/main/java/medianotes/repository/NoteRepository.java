package main.java.medianotes.repository;

import java.util.List;

import main.java.medianotes.model.Note;

//интерфейс для хранилища записок
public interface NoteRepository {
	//описание методов класса	
	Note save(Note note);  //описание метода сохранения записки
	
	List<Note> getAllNotes();  //описание метода получения списка со всеми записками
	
	void remove(String name);  //описание метода удаления записки
}
