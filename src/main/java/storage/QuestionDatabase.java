package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import dialog.Phrases;
import structures.Field;
import structures.Film;
import structures.Hint;
import structures.Options;
import structures.Question;

public class QuestionDatabase {
	
	private static int pageCount = 6;
	private String question = Phrases.DEFAULT_QUESTION;
	private List<Film> films;
	private TheMovieDbApi api;
	private List<Question> questions;
	
	public QuestionDatabase(String apikey) {
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
				options.add(film.getTitle());					
			
			List<Hint> hintsList = new ArrayList<Hint>();
			hintsList.add(new Hint(Phrases.YEAR_HINT, questionFilm.getField(Field.YEAR).get(0)));			
			hintsList.add(new Hint(Phrases.OVERVIEW_HINT, questionFilm.getOverview()));
			
			String image = questionFilm.getImage();
			
			if (image != null) {
				questions.add(
						new Question(
						question,
						options,
						questionFilm.getTitle(),
						hintsList, image));
			}
		}
	}
	
	private List<Film> getFirstPopularFilms(Integer pageCount) throws MovieDbException {
		List<Film> result = new ArrayList<Film>();
		for (int i = 0; i < pageCount; i++) {
			List<MovieInfo> movies = api.getPopularMovieList(i + 1, "ru").getResults();
			
			for (MovieInfo info : movies) {
				Options options = getFilmData(info);
				Film film = new Film(info.getId(), info.getTitle(), options, 
						info.getOverview(), api.createImageUrl(info.getBackdropPath(), "w780").toString());
				result.add(film);
			}
		}
		return result;
	}
	
	private Options getFilmData(MovieInfo info) {
		Options options = new Options();
		options.add(Field.YEAR, info.getReleaseDate().substring(0, 4));
		return options;
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
