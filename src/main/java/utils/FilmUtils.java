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
			String ID = row[0];
			String title = row[1];
			Map<Field, String> filmData = new HashMap<Field, String>();
			for (Field field : Field.values())
				filmData.put(field, row[field.ordinal() + 2]);
			Film film = new Film(ID, title, filmData);
			filmList.add(film);
		}
		return filmList;
	}

	public static List<String[]> filmListToStringList(List<Film> filmList) {
		List<String[]> rowList = new ArrayList<String[]>();
		int rowLength = Field.values().length + 2;
		for (Film film : filmList) {
			String[] filmRow = new String[rowLength];
			filmRow[0] = film.ID;
			filmRow[1] = film.title;
			for (Field field : Field.values())
				filmRow[field.ordinal() + 2] = film.getField(field);
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
}
