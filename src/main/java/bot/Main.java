package bot;

import storage.CSVHandler;
import storage.FileFilmHandler;
import storage.IFilmHandler;
import storage.IFilmDatabaseFileHandler;
import storage.FilmDatabase;
import storage.MovieApiHandler;

import java.util.HashMap;
import java.util.List;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;

import telegram.TelegramChatBot;

public class Main {

	private static FilmDatabase database;

	public static void main(String[] args) throws Exception {
		
		String apikey = "ab2ffab6977110905d92c5979e9ae9fa";
		
		TheMovieDbApi api = new TheMovieDbApi(apikey);	
		
		List<Genre> genreList = api.getGenreMovieList("ru").getResults();
		
//		MovieApiHandler h = new MovieApiHandler()
		
		
		
	
		
		
		
//		TmdbMovies movies = new TmdbApi(apikey).getMovies();
//		TmdbGenre genres = new TmdbApi(apikey).getGenre();
////		TmdbMovies movies = new TmdbApi("<apikey>").getMovies();
//
//		MovieDb movie = movies.getMovie(5353, "en");
//		List<Genre> listGenre = genres.getGenreList("ru");
		
		for (Genre g : genreList) {
			System.out.println(g.getName());
			System.out.println(g.getId());
		}
		
		
		System.out.println(genreList);
		
		

//		IFilmDatabaseFileHandler fileHandler = new CSVHandler("Database");
//		IFilmHandler filmHandler = new FileFilmHandler(fileHandler);
//		database = new FilmDatabase(filmHandler);
//		startTelegramBot();
	}

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(database);
		bot.startTelegramChatBot();

	}

}
