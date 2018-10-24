package dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structures.Film;
import structures.Field;
import structures.User;

public class Dialog {

	private Map<Field, String> currentOptions;
	private Field currentField;

	private User user;
	private Map<Field, Map<String, List<Film>>> filmMapsByField;

	public Dialog(User user, Map<Field, Map<String, List<Film>>> filmMapsByField) {
		this.user = user;
		this.filmMapsByField = filmMapsByField;
		currentField = null;
		currentOptions = new HashMap<Field, String>();
		for (Field field : Field.values())
			currentOptions.put(field, null);
	}

	public String startDialog() {
		if (user.firstTime)
			return String.format("Добро пожаловать, %s.%s", user.name, Phrases.HELP);
		return String.format("Давно не виделись, %s.", user.name);
	}

	public String processInput(String input) {
		if (input.equals("/help"))
			return Phrases.HELP;

		if (input.equals("/next")) {
			if (currentField == null)
				return Phrases.NEXT_WITHOUT_OPT;
			
			return getFilm(currentField, currentOptions.get(currentField));
		}

		if (input.length() < 3)
			return Phrases.SHORT_COMMAND;

		String command = input.substring(0, 3).trim();
		String request = input.substring(3).trim();

		for (Field field : Field.values()) {
			if (command.equals(field.shortCut())) {
				currentField = field;
				return getFilm(field, request);
			}
		}
		return Phrases.UNKNOWN_COMMAND;
	}

	private String getFilm(Field field, String key) {
		
		Map<String, List<Film>> filmsDict = filmMapsByField.get(field);

		if (!filmsDict.containsKey(key)) {
			currentField = null;
			return field.noFilmsAtAll();
		}

		currentOptions.put(field, key);

		String film = getUnshownFilm(filmsDict.get(currentOptions.get(field)));
		return film != null ? film : field.noFilmsLeft();
	}

	private String getUnshownFilm(List<Film> films) {
		for (Film film : films) {
			if (user.savedFilms.contains(film))
				continue;
			user.addFilm(film);
			return film.title;
		}
		return null;
	}
}