package storage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import structures.Field;
import structures.Film;
import utils.FilmUtils;

public class FilmDatabase {

	IFilmHandler filmHandler;

	public FilmDatabase(IFilmHandler filmHandler) {
		this.filmHandler = filmHandler;
	}

	public Film getFilm(Map<Field, List<String>> options, List<String> savedFilmsIDs) {
		List<Film> possibleFilms = filmHandler.getFilmsByOptions(options);
		
		
		
		if (possibleFilms.size() == 0)
			return new Film("None", null, null);
		for (Film film : possibleFilms)
			if (!savedFilmsIDs.contains(film.ID))
				return film;
		return null;
	}


	public String[] getFieldValuesArray(Field field) {
		return filmHandler.getAvaliableFieldValues(field);
	}

	public boolean requestExistInDatabase(Field field, String request) {
		return Arrays.asList(getFieldValuesArray(field)).contains(request);
	}

}
