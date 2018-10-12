package dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structures.Film;
import structures.Field;
import structures.FilmsStructure;
import structures.User;

public class Dialog {

	private Map<Field, String> currentOptions;
	private Field currentField;

	private User user;
	private FilmsStructure filmsStructure;

	public Dialog(User user, FilmsStructure filmsStructure) {
		this.user = user;
		this.filmsStructure = filmsStructure;
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

		if (input.equals("/next"))
			return getNextFilm();

		if (input.length() < 3)
			return Phrases.SHORT_COMMAND;

		String command = input.substring(0, 3).trim();
		String request = input.substring(3).trim();

		for (Field field : Field.values()) {
			if (command.equals(field.shortCut())) {
				currentOptions.put(currentField, null);
				currentField = null;
				return getFilmByRequest(field, request);
			}
		}
		return Phrases.UNKNOWN_COMMAND;
	}

	private String getNextFilm() {
		if (currentField == null)
			return Phrases.NEXT_WITHOUT_OPT;

		Map<String, List<Film>> filmsDict = filmsStructure.getFilmsByField(currentField);
		String film = getUnshownFilm(filmsDict.get(currentOptions.get(currentField)));
		return film != null ? film : currentField.noFilmsLeft();
	}

	private String getFilmByRequest(Field field, String key) {
		Map<String, List<Film>> filmsDict = filmsStructure.getFilmsByField(field);

		if (!filmsDict.containsKey(key))
			return field.noFilmsAtAll();

		currentField = field;
		currentOptions.put(field, key);

		String film = getUnshownFilm(filmsDict.get(key));
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