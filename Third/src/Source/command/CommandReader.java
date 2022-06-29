package Source.command;

import java.util.Scanner;

import Source.Note;
import Source.repository.NoteRepository;
import Source.repository.impl.NoteRepositoryImpl;

//класс обработчик команд
public class CommandReader {
	//поля класса
	
	private static final NoteRepository noteRepository = new NoteRepositoryImpl(); //создаём объект интерфейса для хранения запсок
	
	//методы класса
	
	//метод класса отвечающий за алгоритм работы обработчика команд
	public static int startReadCommand() {
		System.out.println("Programm started! Write your command: "); //выводим приветствующее сообщение
		
		try (Scanner s = new Scanner(System.in)) {
			while(true) { //создаём бесконечный цикл
				var text = s.nextLine(); //читаем команду
				int code = CommandReader.readCommand(text); //передаём команду в обработчик
				
				if(code == 0) { //проверяем событие выхода из приложения
					break;
				}
			}
		}
		return 0;//завершаем приложение
	}
	
	//метод класса отвечающий за обработку команд
	public static int readCommand(String command) {
		if(command.contains("create note")) { //вызываем метод создания записки и возвращаем статус
			return createNote(command);
		}
		
		if(command.contains("notes")) { //вызываем метод вывода записок и возвращаем статус
			return viewAllNotes(command);
		}
		
		if(command.contains("remove note")) { //вызываем метод удаления записки и возвращаем статус
			return removeNote(command);
		}
		
		if(command.contains("exit")) { //вызываем метод выхода из приложения и возвращаем статус
			return 0;
		}
		
		return -1; //возвращаем статус неправильной команды
	}
	
	//метод класса отвечающий за создание записок
	private static int createNote(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
		
		String noteName = words[2]; //создаём строку для хранения названия записки
		
		String noteAuthor = words[3]; //создаём строку для хранения автора записки
		
		StringBuilder noteTextSb = new StringBuilder(); //создаём объект динамической строки для хранения содержимого записки и помещаем туда содержимое
		for(int i = 4; i<words.length; i++) {
			noteTextSb.append(words[i]);
		}
		
		String noteText = noteTextSb.toString(); //приводим объект динамической строки к обычному типу строки
		
		Note newNote = new Note(noteName,noteText,noteAuthor); //создаём записку с полученными полями
		noteRepository.save(newNote);
		
		System.out.println("Note created "); //выводим информирующее сообщение
		
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за отображение всех записок
	private static int viewAllNotes(String command) {
		var notes = noteRepository.getAllNotes(); //получаем список всех записок
		
		for(var note : notes) { //выводим их содержимое
			System.out.printf("Name: %s , Text: %s , Author: %s , CreationDate: %s \n",note.getName(),note.getText(),note.getAuthor(),note.getCreationDate().toString());
		}
		
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за удаление определённой записки
	private static int removeNote(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
		
		String noteName = words[2]; //создаём строку для хранения имени записки
		
		noteRepository.remove(noteName); //удаляем записку по полученному имени
		
		return 1; //возвращаем статус корректного выполнения
	}
}
