package main.java.medianotes.command;

import java.time.Instant;
import java.util.Scanner;

import main.java.medianotes.model.Folder;
import main.java.medianotes.model.Note;
import main.java.medianotes.repository.FolderRepository;
import main.java.medianotes.repository.NoteRepository;
import main.java.medianotes.repository.impl.FolderRepositoryImpl;
import main.java.medianotes.repository.impl.NoteRepositoryImpl;

//класс обработчик команд
public class CommandReader {
	//поля класса
	
	private static final NoteRepository noteRepository = new NoteRepositoryImpl(); //создаём объект интерфейса для хранения запсок
	private static final FolderRepository folderRepository = new FolderRepositoryImpl(); //создаём объект интерфейса для хранения запсок
	private static int user_id; //поле с id вошедшего пользователя
	
	//методы класса
	
	//метод класса отвечающий за алгоритм работы обработчика команд
	public static int startReadCommand(int user_id_in) {
		System.out.println("Programm started! Write your command: "); //выводим приветствующее сообщение
		System.out.println("write \"help\" to view all commands"); //выводим информирующее сообщение
		
		user_id=user_id_in; //сохраняем id вошедшего пользователя
		
		try (Scanner s = new Scanner(System.in)) {
			while(true) { //создаём бесконечный цикл
				System.out.println("************"); //выводим разделитель строк
				var text = s.nextLine(); //читаем команду
				System.out.println("************"); //выводим разделитель строк
				int code = CommandReader.readCommand(text); //передаём команду в обработчик
				
				if(code == 0) { //проверяем событие выхода из приложения
					break;
				}
				
				if(code == -1) { //проверяем событие передачи неправильной команды
					System.out.println("Bad command");
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;//завершаем приложение
	}
	
	//метод класса отвечающий за обработку команд
	public static int readCommand(String command) {
		if(command.contains("create note")) { //вызываем метод создания записки и возвращаем статус
			return createNote(command);
		}
		
		if(command.contains("edit note")) { //вызываем метод редактирования записки и возвращаем статус
			return editNote(command);
		}
		
		if(command.contains("note status set")) { //вызываем метод редактирования статуса записки и возвращаем статус
			return editNoteStatus(command);
		}
		
		if(command.contains("notes")) { //вызываем метод вывода записок и возвращаем статус
			return viewAllNotes(command);
		}
		
		if(command.contains("remove note")) { //вызываем метод удаления записки и возвращаем статус
			return removeNote(command);
		}
		
		if(command.contains("create folder")) { //вызываем метод создания папки и возвращаем статус
			return createFolder(command);
		}
		
		if(command.contains("folders")) { //вызываем метод вывода папок и возвращаем статус
			return viewAllFolders(command);
		}
		
		if(command.contains("remove folder")) { //вызываем метод удаления папки и возвращаем статус
			return removeFolder(command);
		}
		
		if(command.contains("cd")) { //вызываем метод перехода в папку и возвращаем статус
			return chooseDirectory(command);
		}
		
		if(command.contains("ls")) { //вызываем метод отображения содержимого папок и возвращаем статус
			return printDirectory(command);
		}
		
		if(command.contains("help")) { //выводим доступные команды
			System.out.println("-------------------");
			System.out.println("create note NOTENAME NOTEAUTHOR NOTEFOLDER NOTETEXT");
			System.out.println("create note NOTENAME NOTEAUTHOR * NOTETEXT");
			System.out.println("edit note NOTENAME(NOT EDITABLE) NOTEAUTHOR NOTEFOLDER NOTETEXT");
			System.out.println("edit note NOTENAME(NOT EDITABLE) NOTEAUTHOR * NOTETEXT");
			System.out.println("note status set NOTENAME STATUS(Completed , notCompleted)");
			System.out.println("notes");
			System.out.println("remove note NOTENAME");
			System.out.println("create folder FOLDERNAME PARENTFOLDER");
			System.out.println("create folder FOLDERNAME *");
			System.out.println("folders");
			System.out.println("remove folder FOLDERNAME");
			System.out.println("-------------------");
			return -2; //возвращаем статус бездействия
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
		
		String noteFolder = words[4]; //создаём строку для хранения директории записки
		
		Folder falderRes = new Folder("",null,user_id);
		
		if(noteFolder.equals("*")) {
			falderRes=folderRepository.getCurrent(user_id);
		}else {
			boolean res=false; //ищем такую папку и в случае удачи копируем объект
			for(var fal : folderRepository.getFolders(user_id)) {
				if(fal.getName().equals(noteFolder)) {
					res=true;
					falderRes=fal;
					break;
				}
			}
			if(!res) { //если не нашли , то прекращаем выполнение
				System.out.println("Folder not exist");
				return -1; //возвращаем статус неправильной команды
			}
		}
		
		StringBuilder noteTextSb = new StringBuilder(); //создаём объект динамической строки для хранения содержимого записки и помещаем туда содержимое
		for(int i = 5; i<words.length; i++) {
			noteTextSb.append(words[i]);
		}
		
		String noteText = noteTextSb.toString(); //приводим объект динамической строки к обычному типу строки
		
		Note newNote = new Note(noteName,noteText,noteAuthor,falderRes,user_id,"notCompleted",Instant.now()); //создаём записку с полученными полями
		noteRepository.save(newNote,user_id);
		
		System.out.println("Note created "); //выводим информирующее сообщение
		
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за создание папок
	private static int createFolder(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
		
		String folderName = words[2]; //создаём строку для хранения названия папки
		
		String folderParent = words[3]; //создаём строку для хранения родительской папки
		
		boolean res2=true; //ищем такую папку и в случае отсутствия продолжаем работу
		for(var fal : folderRepository.getFolders(user_id)) {
			if(fal.getName().equals(folderName)) {
				res2=false;
				break;
			}
		}
		
		if(!res2) {
			System.out.println("This folder has already been created"); //выводим информирующее сообщение
			return 1;
		}
		
		Folder par; //искомая папка
		
		if(folderParent.equals("*")) {
			par=folderRepository.getCurrent(user_id);
			
			if(par==null) {
				folderRepository.createFolder(folderName,null,user_id);
				System.out.println("Folder created"); //выводим информирующее сообщение
			}else{
				folderRepository.createFolder(folderName,par,user_id);
				System.out.println("Folder created"); //выводим информирующее сообщение
			}
		}else {
			for(var fal : folderRepository.getFolders(user_id)) {
				if(fal.getName().equals(folderParent)) {
					folderRepository.createFolder(folderName,fal,user_id);
					System.out.println("Folder created"); //выводим информирующее сообщение
					break;
				}
			}
		}
		
		
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за просмотр папок 
	private static int viewAllFolders(String command) {
		var folders = folderRepository.getFolders(user_id); //получаем список всех папок
		
		for(Folder folder : folders) { //выводим их содержимое
			System.out.printf("Path: %S \n",folderRepository.getPath(folder.getName(), folder.getParentFolder(), user_id));
		}
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за удаление папок 
	private static int removeFolder(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
		
		String folderName = words[2]; //создаём строку для хранения названия папки
		
		if(folderRepository.getCurrent(user_id)!=null) {
			Folder par; //искомая папка
			par=folderRepository.findFolder(folderName,folderRepository.getCurrent(user_id),user_id);
			if(par==null) {
				System.out.println("Folder not deleted"); //выводим информирующее сообщение
				return 1;
			}else {
				folderRepository.removeFolder(folderName,folderRepository.getCurrent(user_id).getName(),user_id,noteRepository);
				System.out.println("Folder deleted"); //выводим информирующее сообщение
			}
		}else {
			for(var fal : folderRepository.getFolders(user_id)) {
				if(fal.getName().equals(folderName)) {
					if(fal.getParentFolder()!=null) {
						folderRepository.removeFolder(folderName,fal.getParentFolder().getName(),user_id,noteRepository);
					}else {
						folderRepository.removeFolder(folderName,null,user_id,noteRepository);
					}
					System.out.println("Folder deleted"); //выводим информирующее сообщение
					break;
				}
			}
		}
		
		
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за отображение всех записок
	private static int viewAllNotes(String command) {
		var notes = noteRepository.getAllNotes(user_id); //получаем список всех записок
		
		for(var note : notes) { //выводим их содержимое
			System.out.printf("Path: %S , Name: %s , Text: %s , Author: %s , CreationDate: %s , Status: %s \n",note.getParentFolder().getName(),note.getName(),note.getText(),note.getAuthor(),note.getCreationDate().toString(),note.getStatus());
		}
		
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за удаление определённой записки
	private static int removeNote(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
		
		String noteName = words[2]; //создаём строку для хранения имени записки
		
		noteRepository.remove(noteName,user_id); //удаляем записку по полученному имени
		
		System.out.println("Note deleted"); //выводим информирующее сообщение
		
		return 1; //возвращаем статус корректного выполнения
	}
	
	//метод класса отвечающий за переход в папку
	private static int chooseDirectory(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
		
		String folderDir = words[1]; //создаём строку для хранения имени папки в которую переходим
		
		folderRepository.setCurrent(folderDir, user_id);
		
		return 1;
	}
	
	//метод класса отвечающий за переход в папку
	private static int printDirectory(String command) {
		var folders = folderRepository.getFolders(user_id); //получаем список всех папок
		var notes = noteRepository.getAllNotes(user_id); //получаем список всех записок
		Folder curr = folderRepository.getCurrent(user_id);
		
		if(curr==null) {
			for(Folder folder : folders) { //выводим их содержимое
				if((folder.getParentFolder()==null)&&(folder!=null)) {
					System.out.printf("DIR | %S \n",folder.getName());
				}
			}
			
			for(Note note : notes) { //выводим записки
				if((note.getParentFolder()==null)&&(note!=null)) {
					System.out.printf("NOTE | %S | %S | %S | %S | %S | \n",note.getName(),note.getText(),note.getAuthor(),note.getCreationDate().toString(),note.getStatus());
				}
			}
		}else {
			for(Folder folder : folders) { //выводим их содержимое
				if((folder.getParentFolder()!=null)&&(folder!=null)) {
					if(folder.getParentFolder().getName().equals(curr.getName())) {
						System.out.printf("DIR | %S \n",folder.getName());
					}
				}
			}
			
			for(Note note : notes) { //выводим записки
				if((note.getParentFolder()!=null)&&(note!=null)) {
					if(note.getParentFolder().getName().equals(curr.getName())) {
						System.out.printf("NOTE | %S | %S | %S | %S | %S | \n",note.getName(),note.getText(),note.getAuthor(),note.getCreationDate().toString(),note.getStatus());
					}
				}
			}
		}
		
		return 1;
	}
	
	//метод класса отвечающий за изменение статуса записки
	private static int editNoteStatus(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
			
		String noteName = words[3]; //создаём строку для хранения имени записки
		
		String noteStatus = words[4]; //создаём строку для хранения статуса записки
		
		var notes = noteRepository.getAllNotes(user_id); //получаем список всех записок
		
		//проверяем формат статуса
		if((!noteStatus.equals("Completed"))&&(!noteStatus.equals("notCompleted"))) {
			System.out.println("Bad status"); //выводим информирующее сообщение
			return 1;
		}
		
		for(Note note : notes) { //ищем записку
			if(note.getName().equals(noteName)) {
				note.setStatus(noteStatus);
				noteRepository.edit(note, user_id);
			}
		}
			
		return 1;
	}
	
	//метод класса отвечающий за изменение записки
	private static int editNote(String command) {
		String[] words = command.split(" "); //создаём массив слов из которых состоит команда
		
		String noteName = words[2]; //создаём строку для хранения названия записки
		
		String noteAuthor = words[3]; //создаём строку для хранения автора записки
		
		String noteFolder = words[4]; //создаём строку для хранения директории записки
		
		Folder falderRes = new Folder("",null,user_id);
		
		var notes = noteRepository.getAllNotes(user_id); //получаем список всех записок
		
		if(noteFolder.equals("*")) {
			falderRes=folderRepository.getCurrent(user_id);
		}else {
			boolean res=false; //ищем такую папку и в случае удачи копируем объект
			for(var fal : folderRepository.getFolders(user_id)) {
				if(fal.getName().equals(noteFolder)) {
					res=true;
					falderRes=fal;
					break;
				}
			}
			if(!res) { //если не нашли , то прекращаем выполнение
				System.out.println("Folder not exist");
				return -1; //возвращаем статус неправильной команды
			}
		}
		
		StringBuilder noteTextSb = new StringBuilder(); //создаём объект динамической строки для хранения содержимого записки и помещаем туда содержимое
		for(int i = 5; i<words.length; i++) {
			noteTextSb.append(words[i]);
		}
		
		String noteText = noteTextSb.toString(); //приводим объект динамической строки к обычному типу строки
		
		for(Note note : notes) { //ищем записку
			if(note.getName().equals(noteName)) {
				note.setAuthor(noteAuthor);
				note.setParentFolder(falderRes);
				note.setText(noteText);
				noteRepository.edit(note, user_id);
			}
		}
			
		return 1;
	}
}
