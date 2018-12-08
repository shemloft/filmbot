package storage;

import java.util.Arrays;
import java.util.List;

import structures.Field;
import structures.Film;
import structures.Options;
import structures.User;

public class FilmDatabase implements IFilmDatabase{

	IFilmHandler filmHandler;

	public FilmDatabase(IFilmHandler filmHandler) {
		this.filmHandler = filmHandler;
	}

	public Film getFilm(List<Integer> showedFilms, Options options) {
		
		List<Film> possibleFilms = filmHandler.getFilmsByOptions(options);			
		
		if (possibleFilms.size() == 0)
			return new Film(0, null, null);
		for (Film film : possibleFilms)
			if (!showedFilms.contains(film.ID)) {
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
