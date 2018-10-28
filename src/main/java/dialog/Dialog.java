package dialog;

import java.util.List;
import java.util.Map;

import structures.Film;
import structures.Field;
import structures.User;

public class Dialog {	

	private User user;
	private Map<Field, Map<String, List<Film>>> filmMapsByField;

	public Dialog(User user, Map<Field, Map<String, List<Film>>> filmMapsByField) {
		this.user = user;
		this.filmMapsByField = filmMapsByField;		
	}

	public String startDialog() {
		if (user.firstTime)
			return String.format("Добро пожаловать, %s.%s", user.name, Phrases.HELP);
		return String.format("Давно не виделись, %s.", user.name);
	}

	public String processInput(String input) {
		if (input.equals("/help"))
			return Phrases.HELP;

		if (input.equals("/countries"))
			return Phrases.AVAILAIBLE_COUNTRIES;

		if (input.equals("/genres"))
			return Phrases.AVAILAIBLE_GENRES;

		if (input.equals("/next")) {
			if (user.currentField == null)
				return Phrases.NEXT_WITHOUT_OPT;

			return getFilm(user.currentField, user.currentOptions.get(user.currentField));
		}

		if (input.length() < 3)
			return Phrases.SHORT_COMMAND;
		
		System.out.println(input);

		String command = input.substring(0, 3).trim();
		String request = input.substring(3).trim();

		for (Field field : Field.values()) {
			if (command.equals(field.shortCut())) {
				return getFilm(field, request);
			}
		}
		return Phrases.UNKNOWN_COMMAND;
	}

	private String getFilm(Field field, String key) {

		Map<String, List<Film>> filmsDict = filmMapsByField.get(field);

		if (!filmsDict.containsKey(key)) {
			user.clearCurrentField();
			return field.noFilmsAtAll();
		}
		
		user.changeCurrentOption(field, key);	
		
		String film = getUnshownFilm(filmsDict.get(user.getCurrentKey()));
		return film != null ? film : field.noFilmsLeft();
	}

	private String getUnshownFilm(List<Film> films) {
		for (Film film : films) {
			if (user.savedFilmsIDs.contains(film.ID))
				continue;			
			user.addFilm(film);
			return film.title;
		}
		return null;
	}
}