package bot;


import storage.CSVHandler;
import storage.FileFilmHandler;
import storage.IFilmHandler;
import storage.FilmDatabase;
import structures.Field;
import structures.Film;
//import telegram.TelegramChatBot;
import utils.FilmUtils;

public class Main {

	private static FilmDatabase database;

	public static void main(String[] args) throws Exception {
		
		CSVHandler csvHandler = new CSVHandler("Database");
		IFilmHandler filmHandler = new FileFilmHandler(csvHandler);
		database = new FilmDatabase(filmHandler);
		startConsoleBot();
	}

//	public static void startTelegramBot() throws Exception {
//		TelegramChatBot bot = new TelegramChatBot(filmMapsByField);
//		bot.startTelegramChatBot();
//
//	}

	public static void startConsoleBot() throws Exception {
		ChatBot chatBot = new ChatBot(database);
		chatBot.startChat(System.in, System.out);
	}

}
