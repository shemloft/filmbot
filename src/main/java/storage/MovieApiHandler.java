package storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import structures.Field;
import structures.Film;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.tools.Param;


public class MovieApiHandler implements IFilmHandler {
	
	private TheMovieDbApi api;
	private Map<String, Integer> genreIds;
	private Map<Field, String[]> valueArrays;
	
	public MovieApiHandler(String apikey) throws MovieDbException {
		api = new TheMovieDbApi(apikey);
		initializeGenreIds();	
		initializeValueArrays();
	}
	
	private void initializeGenreIds() throws MovieDbException {
		genreIds = new HashMap<String, Integer>();
		List<Genre> genreList = api.getGenreMovieList("ru").getResults();
		
		for (Genre g : genreList) {
			genreIds.put(g.getName(), g.getId());
		}
	}
	
	private void initializeValueArrays() {
		valueArrays = new HashMap<Field, String[]>();
		Set<String> genreSet = genreIds.keySet();
		String[] genres = genreSet.toArray(new String[genreSet.size()]);
		Arrays.sort(genres);
		valueArrays.put(Field.GENRE, genres);
		
		int min = 1900;
		int max = 2025;
		String[] years = new String[max - min + 1];
		for (int i = min; i <= max; i++)
			years[i-min] = String.valueOf(i);
		valueArrays.put(Field.YEAR, years);

	}
	

	public List<Film> getFilmsByOptions(Map<Field, List<String>> options) {
		Discover discover = new Discover();		
		
		List<Film> filmList = new ArrayList<Film>();
		
		processOptions(options, discover);
		
		if (!discover.getParams().has(Param.YEAR) && options.get(Field.YEAR) != null)
			return new ArrayList<Film>();	
		
		List<MovieBasic> result = null;
		
		try {
			result =  api.getDiscoverMovies(discover).getResults();
		} catch (MovieDbException e) {
			return new ArrayList<Film>();
		}	
		
		for (MovieBasic film : result) {
			int id = film.getId();
			MovieInfo info = null;
			try {
				info = api.getMovieInfo(id, "ru");
			} catch (MovieDbException e) {
				continue;
			}
			filmList.add(new Film(id, info.getTitle(), null));			
		}	
		
		return filmList;
	}
	
	private void processOptions(Map<Field, List<String>> options, Discover discover) {
		
		for (Map.Entry<Field, List<String>> entry : options.entrySet()) {
			
			switch(entry.getKey()) {			
			case GENRE:
				String genreString = "";
				for (String genre : entry.getValue()) 
					genreString += (genreString.length() != 0 ? "," : "") + genreIds.get(genre);				
				discover.withGenres(genreString);
				break;
			case YEAR:
				try {			
					discover.year(Integer.parseInt(entry.getValue().get(0)));					
				} catch (NumberFormatException e) {					
				}				
				break;			
			}			
		}		
	}


	public String[] getAvaliableFieldValues(Field field) {		
		return valueArrays.get(field);
	}

}
