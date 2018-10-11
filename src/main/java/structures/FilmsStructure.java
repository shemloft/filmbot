package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FilmsStructure {
	
	private List<Film> filmList;

	private Map<Field, Map<String, List<Film>>> filmMapsByField;

	public FilmsStructure(List<Film> filmList) {
		this.filmList = filmList;
		fillFieldsDictionary();
	}

	public Map<String, List<Film>> getFilmsByField(Field field) {
		return filmMapsByField.get(field);
	}

	private void fillFieldsDictionary() {
		filmMapsByField = new HashMap<Field, Map<String, List<Film>>>();
		for (Field field : Field.values()) 
			filmMapsByField.put(field, createDictionary(field));		
	}

	private Map<String, List<Film>> createDictionary(Field field) {
		Map<String, List<Film>> filmsDictionary = new HashMap<String, List<Film>>();
		for (Film film : filmList) {
			String key = film.getField(field);
			if (!filmsDictionary.containsKey(key)) {
				List<Film> filmListByKey = new ArrayList<Film>();
				filmListByKey.add(film);
				filmsDictionary.put(key, filmListByKey);
			} else {
				filmsDictionary.get(key).add(film);
			}
		}
		return filmsDictionary;
	}

}
