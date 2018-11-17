package dialog;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import storage.FilmDatabase;
import structures.Film;
import structures.Field;
import structures.User;
import utils.FilmUtils;

public class Dialog {
	 
	/* нужно обработать некст - для этого указания в юзере
	 * дописать код там и здесь добавить соответсвующие команды
	 * 
	 * парс add
	 * 
	 * парс простой команды можно вынести в отдельную функцию 
	 * 
	 * или создать класс с утилями для парсинга, но возможно это слишком*/

	private User user;
	private FilmDatabase database;

	public Dialog(User user, FilmDatabase database) {
		this.user = user;
		this.database = database;
	}

	public String startDialog() {
		if (user.firstTime)
			return String.format("Добро пожаловать, %s.%s", user.name, Phrases.HELP);
		return String.format("Давно не виделись, %s.", user.name);
	}

	public String processInput(String input) {
		if (input.length() < 3)
			return Phrases.SHORT_COMMAND;
		
		if (input.equals("/help"))
			return Phrases.HELP;

		if (input.equals("/countries"))
			return Phrases.AVAILAIBLE_COUNTRIES;

		if (input.equals("/genres"))
			return Phrases.AVAILAIBLE_GENRES;

		if (input.equals("/years"))
			return Phrases.AVAILAIBLE_YEARS;

		if (input.equals("/add"))
			return "";

//		if (input.equals("/next")) {
//			if (user.currentField == null)
//				return Phrases.NEXT_WITHOUT_OPT;
//
//			return getFilm(user.currentField, user.currentOptions.get(user.currentField));
//		}
		
		String[] commandArray = input.split(" ");
		if (commandArray.length % 2 != 0)
			return Phrases.UNKNOWN_COMMAND;
		
		Map<Field, List<String>> commands = new HashMap<Field, List<String>>();
		
		for (int i = 0; i < commandArray.length; i += 2) {
			
			boolean knownField = false;
			for (Field field : Field.values()) {
				if (!commandArray[i].equals(field.shortCut()))
					continue;
				
				if (commands.get(field) == null) 
					commands.put(field, new ArrayList<String>());
					
				commands.get(field).add(commandArray[i + 1]);
				knownField = true;
			}		
			if (!knownField)
				return Phrases.UNKNOWN_COMMAND;
		}
		
		
		
		
		
		return getFilm(commands);
		
		
		
//		это конечно мусор но удалять жалко
//		Scanner scan = new Scanner(System.in);
//		PrintStream printStream = new PrintStream(System.out);
//
//		Map<Field, List<String>> commands = new HashMap<Field, List<String>>();
//		while (true) {
//			String[] command = input.trim().split("\\s", 2);
//			for (Field field : Field.values())
//				if (commands.keySet().contains(field) && command[0].equals(field.shortCut())) {
//					commands.get(field).add(command[1]);
//				} else {
//					commands.put(field, new ArrayList<String>());
//				}
//
//			printStream.println("Дополнительный параметр?[да/нет]");
//			String answer = scan.nextLine();
//			if (answer.equals("да"))
//				input = scan.nextLine();
//
//			else if (answer.equals("нет"))
//				break;
//			else
//				return Phrases.UNKNOWN_COMMAND;
//		}
//		scan.close();
//		return getFilm(commands);
//		for (Field field : Field.values()) {
//			if (command.equals(field.shortCut())) {
//				return getFilm(field, request);
//			}
//		}return Phrases.UNKNOWN_COMMAND;

	}

	private String getFilm(Map<Field, List<String>> commands) {
//		Map<String, List<Film>> filmsDict = filmMapsByField.get(field);

//		if (!filmsDict.containsKey(key)) {
//			user.clearCurrentField();
//			return field.noFilmsAtAll();
//		}
//там в юзере надо будет ещё поменять что-то, но я запуталась
		
//	я написала в юзере что менять
//		user.changeCurrentOption(field, key);
		
		Film film = database.getFilm(commands, user.savedFilmsIDs);
		if (film != null)
			user.addFilm(film);
		
		
		

		return film != null ? film.title : Phrases.NO_SUCH_FILM;
	}

}