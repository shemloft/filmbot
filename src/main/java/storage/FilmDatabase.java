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
	
	private void cacheFilms(Options options, List<Film> filmList) {
		for(Field field : Field.values()) {
			List<String> fieldValues = options.getFieldValues(field);
			if (fieldValues == null)
				continue;
			for (String fieldValue : fieldValues) {
				for (Film film : filmList) {
					cachedFilms.putIfAbsent(field, new ConcurrentHashMap<String, List<Film>>());
					cachedFilms.get(field).putIfAbsent(fieldValue, new ArrayList<Film>());
					cachedFilms.get(field).get(fieldValue).add(film); // может добавиться несколько раз
				}
			}
		}
	}
	
//	private List<Film> tryToGetCashedFilms(List<Integer> showedFilms, Options options) {
//		List<Film> filmsByOptions = new ArrayList<Film>();
//		outerloop: for (Film film : filmList) {
//			for (Entry<Field, List<String>> entry : options.entrySet()) {
//				Field field = entry.getKey();
//				List<String> fieldOptions = entry.getValue();
//				for (String option : fieldOptions) {
//					if (!film.getField(field).contains(option))
//						continue outerloop;
//				}
//			}
//			filmsByOptions.add(film);
//		}
//		return filmsByOptions;
//		
//	}


	public String[] getFieldValuesArray(Field field) {
		return filmHandler.getAvaliableFieldValues(field);
	}

	public boolean requestExistInDatabase(Field field, String request) {
		return Arrays.asList(getFieldValuesArray(field)).contains(request);
	}

}
