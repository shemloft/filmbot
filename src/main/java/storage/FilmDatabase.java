package storage;

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
		for (Film film : possibleFilms) 
			if (!savedFilmsIDs.contains(film.ID))
				return film;
		return null;	
	}
	
	public void addFilmToDatabase(String title, String country, String year, String genre) throws Exception {
		Film film = FilmUtils.getFilm(filmHandler.getFilmsCount().toString(), title, country, year, genre);
		filmHandler.addFilm(film);
	}
}
