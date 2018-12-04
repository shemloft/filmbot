package storage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import structures.Field;
import structures.Film;
import structures.User;
//import utils.FilmUtils;

public class FilmDatabase {

	IFilmHandler filmHandler;

	public FilmDatabase(IFilmHandler filmHandler) {
		this.filmHandler = filmHandler;
	}

	public Film getFilm(User user) {
		
		List<Film> possibleFilms = filmHandler.getFilmsByOptions(user.currentOptions);	
		
		
		if (possibleFilms.size() == 0)
			return new Film(0, null, null);
		for (Film film : possibleFilms)
			if (!user.savedFilmsIDs.contains(film.ID)) {
				return film;
			}
		return null;
	}


	public String[] getFieldValuesArray(Field field) {
		return filmHandler.getAvaliableFieldValues(field);
	}

	public boolean requestExistInDatabase(Field field, String request) {
		return Arrays.asList(getFieldValuesArray(field)).contains(request);
	}

}
