package bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User 
{
	public String Name;
	private Map<String, ArrayList<Film>> savedFilmsByYear;
	private Map<String, ArrayList<Film>> savedFilmsByCountry;
	
	public User(String name) {
		Name = name;
		savedFilmsByYear = new HashMap<String, ArrayList<Film>>();
		savedFilmsByCountry = new HashMap<String, ArrayList<Film>>(); 		
	}
	
	public ArrayList<Film> getSavedFilmsYear(String year)
	{
		if (!savedFilmsByYear.containsKey(year))
			return null;
		return savedFilmsByYear.get(year);		
	}
	
	public ArrayList<Film> getSavedFilmsCountry(String country)
	{
		if (!savedFilmsByCountry.containsKey(country))
			return null;
		return savedFilmsByCountry.get(country);		
	}
	
	public void addFilmByYear(Film film)
	{
		String year = film.getYear();
		if (!savedFilmsByYear.containsKey(year)) {
			ArrayList<Film> filmList = new ArrayList<Film>();
			filmList.add(film);
			savedFilmsByYear.put(year, filmList);
		} else {
			savedFilmsByYear.get(year).add(film);
		}
	}
	
	public void addFilmByCountry(Film film)
	{
		String country = film.getCountry();
		if (!savedFilmsByCountry.containsKey(country)) {
			ArrayList<Film> filmList = new ArrayList<Film>();
			filmList.add(film);
			savedFilmsByCountry.put(country, filmList);
		} else {
			savedFilmsByCountry.get(country).add(film);
		}
	}

}
