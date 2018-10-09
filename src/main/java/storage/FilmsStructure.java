package storage;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import logic.Film;

public class FilmsStructure 
{
	public ArrayList<Film> filmList;
	private Map<String, ArrayList<Film>> filmsByYear;
	private Map<String, ArrayList<Film>> filmsByCountry;
	
	public FilmsStructure(ArrayList<Film> filmList) throws KeyException
	{
		this.filmList = filmList;
		filmsByYear = createDictionary("year");
		filmsByCountry = createDictionary("country");
	}
	
	public Map<String, ArrayList<Film>> getFilmsByKey(String keyName)
	{
		if (keyName.equals("year"))
			return filmsByYear;
		if (keyName.equals("country"))
			return filmsByCountry;
		return null;
	}
	
 	private Map<String, ArrayList<Film>> createDictionary(String keyName)
 	{
 		Map<String, ArrayList<Film>> filmsDictionary = new HashMap<String, ArrayList<Film>>();
		for (Film film: filmList) {			
			String key = film.getField(keyName);
			if (!filmsDictionary.containsKey(key)) {
				ArrayList<Film> filmListByKey = new ArrayList<Film>();
				filmListByKey.add(film);
				filmsDictionary.put(key, filmListByKey);
			} else {
				filmsDictionary.get(key).add(film);
			}
		}
		return filmsDictionary;
 	}

}
