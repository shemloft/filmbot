package bot;

import storage.IFilmHandler;
import storage.FilmDatabase;
import storage.MovieApiHandler;
import storage.QuestionDatabase;
import telegram.TelegramChatBot;

public class Main {

	private static FilmDatabase database;
	private static QuestionDatabase qDatabase;

	public static void main(String[] args) throws Exception {
		
		String apikey = "ab2ffab6977110905d92c5979e9ae9fa";
//		String apikey = "<apikey>";	

		IFilmHandler filmHandler = new MovieApiHandler(apikey);
		
		database = new FilmDatabase(filmHandler);
		qDatabase = new QuestionDatabase(apikey);
		startTelegramBot();
	}

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(database, qDatabase);
		bot.startTelegramChatBot();

	}

}
