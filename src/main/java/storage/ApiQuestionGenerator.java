package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.enumeration.ArtworkType;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.artwork.Artwork;
import com.omertron.themoviedbapi.model.movie.MovieInfo;

import structures.Field;
import structures.Film;
import structures.Question;

public class ApiQuestionGenerator implements IQuestionGenerator{
	
	private String question = "Из какого фильма данный кадр?";
	private List<Film> films;
	private Map<Film, String> images;
	private TheMovieDbApi api;
	private Random rnd = new Random(System.currentTimeMillis());
	
	public ApiQuestionGenerator(String apikey) {
		try {
			images = new HashMap<Film, String>();
			api = new TheMovieDbApi(apikey);
			films = getFirstPopularFilms(4);
		} catch (MovieDbException e) {
//			e.printStackTrace();			
		}		
	}

	@Override
	public Question getNextQuestion() {
		List<Film> questionFilms = pickRandomFromFilms(4);
		int index = rnd.nextInt(4);
		Film questionFilm = questionFilms.get(index);
		String[] options = new String[questionFilms.size()];
		for (int i = 0; i < options.length; i++) {
			options[i] = questionFilms.get(i).title;
		}
		
		List<Pair<Field, String>> hintsList = new ArrayList<Pair<Field, String>>();
		if (questionFilm.getField(Field.GENRE).size() > 0) {
			hintsList.add(Pair.of(Field.GENRE, questionFilm.getField(Field.GENRE).get(0)));
		}
		hintsList.add(Pair.of(Field.YEAR, questionFilm.getField(Field.YEAR).get(0)));
		
		String image = getImage(questionFilm.ID);
		images.put(questionFilm, image);
		
		return new Question(
				question,
				options,
				options[index],
				hintsList, image);
	}
	
	private List<Film> getFirstPopularFilms(Integer pageCount) throws MovieDbException {
		List<Film> result = new ArrayList<Film>();
		for (int i = 0; i < pageCount; i++) {
			List<MovieInfo> movies = api.getPopularMovieList(i + 1, "ru").getResults();
			
			for (MovieInfo info : movies) {
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
		if (info.getGenres() != null) {
			for (Genre g : info.getGenres()) {
				genres.add(g.getName());
			}
		}
		List<String> year = new ArrayList<String>();
		year.add(info.getReleaseDate());
		filmData.put(Field.GENRE, genres);
		filmData.put(Field.YEAR, year);
		return filmData;
	}
	
	private List<Film> pickRandomFromFilms(int count) {
		List<Film> copy = new LinkedList<Film>(films);
	    Collections.shuffle(copy);
	    return copy.subList(0, count);
	}
	
	private String getImage(int id) {
		try {
		List<Artwork> pics = api.getMovieImages(id, "").getResults();
	      for (Artwork pic : pics) {
	        if (pic.getArtworkType() == ArtworkType.BACKDROP) { 
	        	return "https://image.tmdb.org" + api.createImageUrl(pic.getFilePath(), "original").getPath();
	        }
	      }
	      return null;
		}
		catch (MovieDbException e) {
			e.printStackTrace();
			return null;
		}
	}

}
