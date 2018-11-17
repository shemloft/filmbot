package bot;

import storage.CSVHandler;
import storage.IDatabase;
import storage.DatabaseFilmHandler;
import storage.FilmHandler;
import structures.Field;
import structures.Film;
//import telegram.TelegramChatBot;
import utils.FilmUtils;

public class Main {

	private static IDatabase database;

	public static void main(String[] args) throws Exception {
		database = new CSVHandler("Database");
//		Phrases.COUNTRIES = FilmUtils.getParametersList(Field.COUNTRY, filmMapsByField);
//	    Phrases.GENRES = FilmUtils.getParametersList(Field.GENRE, filmMapsByField);
//	    Phrases.YEARS = FilmUtils.getParametersList(Field.YEAR, filmMapsByField);
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
