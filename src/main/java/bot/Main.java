package bot;

import storage.IFilmHandler;
import storage.FilmDatabase;
import storage.MovieApiHandler;
import telegram.TelegramChatBot;

public class Main {

	private static FilmDatabase database;

	public static void main(String[] args) throws Exception {
		
		String apikey = "ab2ffab6977110905d92c5979e9ae9fa";
//		String apikey = "<apikey>";	

		IFilmHandler filmHandler = new MovieApiHandler(apikey);
		
		database = new FilmDatabase(filmHandler);
		startTelegramBot();
	}

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(database);
		bot.startTelegramChatBot();

	}

}
