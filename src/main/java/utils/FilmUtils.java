package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structures.Field;
import structures.Film;

public class FilmUtils {

	public static List<Film> stringListToFilmList(List<String[]> stringList) {
		List<Film> filmList = new ArrayList<Film>();
		for (String[] row : stringList) {			
			Film film = stringArrayToFilm(row);
			filmList.add(film);
		}
		return filmList;
	}
	
	public static Film stringArrayToFilm(String[] filmString) {
		String ID = filmString[0];
		String title = filmString[1];
		Map<Field, String> filmData = new HashMap<Field, String>();
		for (Field field : Field.values())
			filmData.put(field, filmString[field.ordinal() + 2]);
		Film film = new Film(ID, title, filmData);
		return film;
	}
	
	public static String[] filmToStringArray(Film film) {
		String[] filmString = new String[Field.values().length + 2];
		filmString[0] = film.ID;
		filmString[1] = film.title;
		for (Field field : Field.values())
			filmString[field.ordinal() + 2] = film.getField(field);
		return filmString;
	}

	public static List<String[]> filmListToStringList(List<Film> filmList) {
		List<String[]> rowList = new ArrayList<String[]>();
		for (Film film : filmList) {
			String[] filmRow = filmToStringArray(film);
			rowList.add(filmRow);
		}
		return rowList;
	}

	public static Map<Field, Map<String, List<Film>>> getFilmMapsByField(List<Film> filmList) {
		Map<Field, Map<String, List<Film>>> filmMapsByField = new HashMap<Field, Map<String, List<Film>>>();
		for (Field field : Field.values())
			filmMapsByField.put(field, createMap(filmList, field));
		return filmMapsByField;
	}

	public static Map<String, List<Film>> createMap(List<Film> filmList, Field field) {
		Map<String, List<Film>> filmsMap = new HashMap<String, List<Film>>();
		for (Film film : filmList) {
			String[] keys = film.getField(field).split(", ");
			for (String key : keys) {
				if (!filmsMap.containsKey(key)) {
					List<Film> filmListByKey = new ArrayList<Film>();
					filmListByKey.add(film);
					filmsMap.put(key, filmListByKey);
				} else {
					filmsMap.get(key).add(film);
				}
			}
		}
		return filmsMap;
	}

	public static Film getFilm(String id, String title, String country, String year, String genre) {
		Map<Field, String> filmData = new HashMap<Field, String>();
		filmData.put(Field.COUNTRY, country);
		filmData.put(Field.YEAR, year);
		filmData.put(Field.GENRE, genre);
		return new Film(id, title, filmData);
	}
	
	public static Map<Field, String[]> getOptionValuesMap(Map<Field, Map<String, List<Film>>> filmMapsByField) {
		Map<Field, String[]> optionsValuesMap = new HashMap<Field, String[]>(); 
		for (Field field : Field.values())
			optionsValuesMap.put(field, getOptionValues(field, filmMapsByField));
		return optionsValuesMap;
	}
	
	public static String[] getOptionValues(Field field, Map<Field, Map<String, List<Film>>> filmMapsByField) {
	    List<String> parameterList = new ArrayList<String>();
	    if (filmMapsByField != null)
	    	parameterList.addAll(filmMapsByField.get(field).keySet());
	    return parameterList.toArray(new String[parameterList.size()]);
	  }
}
