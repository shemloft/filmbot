package bot;

import storage.CSVHandler;
import storage.FileFilmHandler;
import storage.IFilmHandler;
import storage.IFilmDatabaseFileHandler;
import storage.FilmDatabase;
import telegram.TelegramChatBot;

public class Main {

	private static FilmDatabase database;

	public static void main(String[] args) throws Exception {

		IFilmDatabaseFileHandler fileHandler = new CSVHandler("Database");
		IFilmHandler filmHandler = new FileFilmHandler(fileHandler);
		database = new FilmDatabase(filmHandler);
		startTelegramBot();
	}

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(database);
		bot.startTelegramChatBot();

	}

	public static void startConsoleBot() throws Exception {
		ChatBot chatBot = new ChatBot(database);
		chatBot.startChat(System.in, System.out);
	}

}
