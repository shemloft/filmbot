package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import dialog.Phrases;
import structures.Field;
import structures.Film;
import structures.Hint;
import structures.Question;

public class QuestionDatabase {
	
	private static int pageCount = 6;
	private String question = Phrases.DEFAULT_QUESTION;
	private List<Film> films;
	private TheMovieDbApi api;
	private List<Question> questions;
	private Map<Integer,String> picturesPaths;
	private Map<Integer,String> filmsOverviews;
	
	public QuestionDatabase(String apikey) {
		picturesPaths = new HashMap<Integer,String>();
		filmsOverviews = new HashMap<Integer,String>(); 
		try {
			api = new TheMovieDbApi(apikey);
			films = getFirstPopularFilms(pageCount);
			initQuestions();
		} catch (MovieDbException e) {
//			e.printStackTrace();			
		}		
	}

	public void initQuestions() {
		questions = new ArrayList<Question>();
		
		
		for (int i = 0; i < films.size(); i++) {
			List<Film> copy = new ArrayList<Film>(films);
			copy.remove(i);
			List<Film> questionFilms = pickRandomFromList(copy, 3);
			Film questionFilm = films.get(i);
			questionFilms.add(questionFilm);
			Collections.shuffle(questionFilms);
			List<String> options = new ArrayList<String>();
			for (Film film : questionFilms)
				options.add(film.title);					
			
			List<Hint> hintsList = new ArrayList<Hint>();
			hintsList.add(new Hint(Phrases.YEAR_HINT, questionFilm.getField(Field.YEAR).get(0)));			
			hintsList.add(new Hint(Phrases.OVERVIEW_HINT, filmsOverviews.get(questionFilm.ID)));
			
			String image = picturesPaths.get(questionFilm.ID);
			
			if (image != null) {
				questions.add(
						new Question(
						question,
						options,
						questionFilm.title,
						hintsList, image));
			}
		}
	}
	
	private List<Film> getFirstPopularFilms(Integer pageCount) throws MovieDbException {
		List<Film> result = new ArrayList<Film>();
		for (int i = 0; i < pageCount; i++) {
			List<MovieInfo> movies = api.getPopularMovieList(i + 1, "ru").getResults();
			
			for (MovieInfo info : movies) {
				picturesPaths.put(info.getId(), api.createImageUrl(info.getBackdropPath(), "w780").toString());
				filmsOverviews.put(info.getId(), info.getOverview());
				Map<Field, List<String>> filmData = getFilmData(info);
				Film film = new Film(info.getId(), info.getTitle(), filmData);
				result.add(film);
			}
		}
		return result;
	}
	
	private Map<Field, List<String>> getFilmData(MovieInfo info) {
		Map<Field, List<String>> filmData = new HashMap<Field, List<String>>();
		List<String> genres = new ArrayList<String>();
		
		List<String> year = new ArrayList<String>();
		year.add(info.getReleaseDate());
		filmData.put(Field.GENRE, genres);
		filmData.put(Field.YEAR, year);
		return filmData;
	}
	
	private List<Film> pickRandomFromList(List<Film> filmList, int count) {
		List<Film> copy = new LinkedList<Film>(filmList);
	    Collections.shuffle(copy);
	    return copy.subList(0, count);
	}
	
	
	public List<Question> getAllQuestions() {
		return questions;
	}

}
