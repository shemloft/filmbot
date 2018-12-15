package storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import structures.Field;
import structures.Film;
import structures.Options;

public class FilmDatabase implements IFilmDatabase{

	private IFilmHandler filmHandler;
	private ConcurrentHashMap<Field, ConcurrentHashMap<String, List<Film>>> cachedFilms;

	public FilmDatabase(IFilmHandler filmHandler) {
		this.filmHandler = filmHandler;
		cachedFilms = new ConcurrentHashMap<Field, ConcurrentHashMap<String, List<Film>>>();
	}

	public Film getFilm(List<Integer> showedFilms, Options options) {
		List<Film> cachedFilms = tryToGetCashedFilms(options);
		Film cachedFilm = cachedFilms == null ? null : getUnshowedFilm(showedFilms, tryToGetCashedFilms(options));
		if (cachedFilm != null && cachedFilm.getID() != 0)
			return cachedFilm;
		System.out.println("kek");
		
		List<Film> possibleFilms = filmHandler.getFilmsByOptions(options);
		cacheFilms(options, possibleFilms);
		
		return getUnshowedFilm(showedFilms, possibleFilms);
	}
	
	private Film getUnshowedFilm(List<Integer> showedFilms, List<Film> possibleFilms) {
		if (possibleFilms.size() == 0)
			return new Film(0, null, null, null, null);
		for (Film film : possibleFilms)
			if (!showedFilms.contains(film.getID())) {
				return film;
			}
		return null;
	}
	
	private void cacheFilms(Options options, List<Film> filmList) {
		for(Field field : options.optionsFields()) {
			for (String fieldValue : options.getFieldValues(field)) {
				for (Film film : filmList) {
					cachedFilms.putIfAbsent(field, new ConcurrentHashMap<String, List<Film>>());
					cachedFilms.get(field).putIfAbsent(fieldValue, new ArrayList<Film>());
					if (!cachedFilms.get(field).get(fieldValue).contains(film))
						cachedFilms.get(field).get(fieldValue).add(film);
				}
			}
		}
	}
	
	private List<Film> tryToGetCashedFilms(Options options) {
		List<Film> filmsByOptions = new ArrayList<Film>();
		if (options.isEmpty())
			return null;
		Field firstField = options.optionsFields().get(0);
		String firstFieldFirstValue = options.getFieldValues(firstField).get(0);
		
		if (cachedFilms.get(firstField) == null ||
			cachedFilms.get(firstField).get(firstFieldFirstValue) == null)
			return null;
		
		filmsByOptions = cachedFilms.get(firstField).get(firstFieldFirstValue);
		
		for (Field field : options.optionsFields()) {			
			if (cachedFilms.get(field) == null)
				return null;
			List<String> fieldValues = options.getFieldValues(field);			
			for (String fieldValue : fieldValues) {
				List<Film> filmsByValue = cachedFilms.get(field).get(fieldValue);				
				if (filmsByValue == null)
					return null;
				List<Film> filmsWithoutField = new ArrayList<Film>();
				for (Film film : filmsByOptions) {
					if (!filmsByValue.contains(film))
						filmsWithoutField.add(film);															
				}
				filmsByOptions.removeAll(filmsWithoutField);
			}
		}
		return filmsByOptions;
		
	}


	public String[] getFieldValuesArray(Field field) {
		return filmHandler.getAvaliableFieldValues(field);
	}

	public boolean requestExistInDatabase(Field field, String request) {
		return Arrays.asList(getFieldValuesArray(field)).contains(request);
	}

}
