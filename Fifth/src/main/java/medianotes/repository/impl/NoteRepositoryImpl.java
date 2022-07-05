package main.java.medianotes.repository.impl;

import java.util.LinkedList;
import java.util.List;

import main.java.medianotes.model.Note;
import main.java.medianotes.repository.NoteRepository;

//класс хранилища записок
public class NoteRepositoryImpl implements NoteRepository{
	//поля класса
	
	private static final List<Note> NOTES = new LinkedList<>(); //список со всеми записками
	
	//методы класса
	
	//метод сохранения записки
	@Override
	public Note save(Note note) {
		NOTES.add(note);
		
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
	}
}
