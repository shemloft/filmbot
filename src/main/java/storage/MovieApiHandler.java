package storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import structures.Field;
import structures.Film;
import structures.Options;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.tools.Param;


public class MovieApiHandler implements IFilmHandler {
	
	private TheMovieDbApi api;
	private Map<String, Integer> genreIds;
	private Map<String, Integer> peopleIds;
	private Map<Field, String[]> valueArrays;
	
	public MovieApiHandler(String apikey)  {
		try {
			api = new TheMovieDbApi(apikey);
			initializeGenreIds();
			initializePeopleIds();	
			initializeValueArrays();
		} catch (MovieDbException e) {
//			e.printStackTrace();			
		}		
	}
	
	private void initializeGenreIds() throws MovieDbException {
		genreIds = new HashMap<String, Integer>();
		List<Genre> genreList = api.getGenreMovieList("ru").getResults();
		System.out.println(genreList);
		for (Genre g : genreList) {
			genreIds.put(g.getName(), g.getId());
		}
	}
	
	private void initializePeopleIds() throws MovieDbException {
		peopleIds = new HashMap<String, Integer>();
		getFirstPopularPeople(5);
	}
	
	private void getFirstPopularPeople(Integer pageCount) throws MovieDbException {
		for (int i = 0; i < pageCount; i++) {
			List<PersonFind> peopleList = api.getPersonPopular(i + 1).getResults();
			
			for (PersonFind g : peopleList) {
				peopleIds.put(g.getName(), g.getId());
			}
		}
	}
	
	private void initializeValueArrays() {
		valueArrays = new HashMap<Field, String[]>();
		Set<String> genreSet = genreIds.keySet();
		String[] genres = genreSet.toArray(new String[genreSet.size()]);
		Arrays.sort(genres);
		valueArrays.put(Field.GENRE, genres);
		
		Set<String> peopleSet = peopleIds.keySet();
		String[] people = peopleSet.toArray(new String[peopleSet.size()]);
		Arrays.sort(people);
		valueArrays.put(Field.ACTOR, people);
		
		int min = 1900;
		int max = 2025;
		String[] years = new String[max - min + 1];
		for (int i = min; i <= max; i++)
			years[i-min] = String.valueOf(i);
		valueArrays.put(Field.YEAR, years);

	}
	

	public List<Film> getFilmsByOptions(Options options) {
		Discover discover = new Discover();		
		
		List<Film> filmList = new ArrayList<Film>();
		
		processOptions(options, discover);
		
		if (!discover.getParams().has(Param.YEAR) && options.getFieldValues(Field.YEAR) != null)
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
			if (!checkFilmInfo(id, options))
				continue;
			filmList.add(new Film(id, info.getTitle(), null));			
		}	
		
		return filmList;
	}
	
	private boolean checkFilmInfo(int id, Options options) {
		MovieInfo info = null;
		try {
			info = api.getMovieInfo(id, "ru");
			String year = info.getReleaseDate().substring(0, 4);
			if (options.getFieldValues(Field.YEAR) != null && !options.getFieldValues(Field.YEAR).get(0).equals(year))
				return false;
			if (options.getFieldValues(Field.GENRE) == null)
				return true;
			for (String genre : options.getFieldValues(Field.GENRE)) {
				List<Genre> genreList = info.getGenres();
				boolean found = false;
				for (Genre g : genreList) {
					if (g.getId() == genreIds.get(genre))
							found = true;
				}
				if (!found)
					return false;
			}
		} catch (MovieDbException e) {
			return false;
		}
		return true;
	}
	
	private void processOptions(Options options, Discover discover) {
		for (Field field : Field.values()) {
			List<String> fieldValues = options.getFieldValues(field);
			if (fieldValues != null)
				processFieldValue(field, fieldValues, discover);				
		}	
	}
	
	private void processFieldValue(Field field, List<String> fieldValues, Discover discover) {
		switch(field) {			
		case GENRE:
			String genreString = "";
			for (String genre : fieldValues) 
				genreString += (genreString.length() != 0 ? "," : "") + genreIds.get(genre);				
			discover.withGenres(genreString);
			break;
		case YEAR:
			try {			
				discover.year(Integer.parseInt(fieldValues.get(0)));					
			} catch (NumberFormatException e) {					
			}				
			break;
		case ACTOR:
			String actorsString = "";
			for (String actor : fieldValues) 
				actorsString += (actorsString.length() != 0 ? "," : "") + peopleIds.get(actor);				
			discover.withCast(actorsString);
			break;
		}	
		
	}


	public String[] getAvaliableFieldValues(Field field) {		
		return valueArrays.get(field);
	}

}
