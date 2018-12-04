package storage;

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


//import info.movito.themoviedbapi.TmdbGenre;
//import info.movito.themoviedbapi.TmdbDiscover; 
//import info.movito.themoviedbapi.model.Genre;
//import info.movito.themoviedbapi.model.MovieDb;
//import info.movito.themoviedbapi.model.core.MovieResultsPage;
//import info.movito.themoviedbapi.model.Discover;

public class MovieApiHandler implements IFilmHandler {
	
	String apikey = "ab2ffab6977110905d92c5979e9ae9fa";
	TheMovieDbApi api;
	Map<String, Integer> genreIds;
	
	public MovieApiHandler() throws MovieDbException {
		
		api = new TheMovieDbApi(apikey);	
		
		genreIds = new HashMap<String, Integer>();
		List<Genre> genreList = api.getGenreMovieList("ru").getResults();
		
		for (Genre g : genreList) {
			genreIds.put(g.getName(), g.getId());
		}
		
		
	}
	

	public List<Film> getFilmsByOptions(Map<Field, List<String>> options) {
		Discover discover = new Discover();	
		
		for (Map.Entry<Field, List<String>> entry : options.entrySet()) {
			switch(entry.getKey()) {
			case COUNTRY:
				break;
			case GENRE:
				String genreString = "";
				for (String genre : entry.getValue()) 
					genreString += (genreString.length() != 0 ? "," : "") + genreIds.get(genre);				
				discover.withGenres(genreString);
				break;
			case YEAR:
				break;
			default:
				break;
			
			}
		}
		List<MovieBasic> result = null;
		
		try {
			result =  api.getDiscoverMovies(discover).getResults();
		} catch (MovieDbException e) {
			e.printStackTrace();
		}
		
//		for (MovieBasic r : result)
//			r.
		
		
		
		return null;
	}
	
	private Discover getDiscover() {
		Discover discover = new Discover();	
		
		return discover;		
	}

	public String[] getAvaliableFieldValues(Field field) {
		
		switch(field) {
		case COUNTRY:
			break;
		case GENRE:
			Set<String> genreSet = genreIds.keySet();
			String[] genres = genreSet.toArray(new String[genreSet.size()]);
			Arrays.sort(genres);
			return genres;
		case YEAR:
			break;
		default:
			break;
		
		}
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
